package ru.kardo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.kardo.dto.feed.CreateFeedDto;
import ru.kardo.dto.feed.FeedFullDto;
import ru.kardo.dto.feed.UpdateFeedDto;
import ru.kardo.dto.profile.ProfilePreviewDtoResponse;
import ru.kardo.exception.ConflictException;
import ru.kardo.exception.NotFoundValidationException;
import ru.kardo.mapper.FeedMapper;
import ru.kardo.model.Feed;
import ru.kardo.model.FeedMedia;
import ru.kardo.model.Profile;
import ru.kardo.repo.FeedMediaRepo;
import ru.kardo.repo.FeedRepo;
import ru.kardo.repo.ProfileRepo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService {

    private final FeedRepo feedRepo;
    private final FeedMediaRepo feedMediaRepo;
    private final ProfileRepo profileRepo;
    private final ProfileService profileService;

    private final FeedMapper feedmapper;

    public FeedFullDto addNewPost(Long userId, CreateFeedDto dto) {
        Profile owner = checkIfProfileExist(userId);
        Set<FeedMedia> media = saveMedia(dto.getMedia(), owner.getUser().getEmail());
        Feed feed = new Feed();
        feed.setOwner(owner);
        feed.setMedia(media);
        feed.setDescription(dto.getDescription());
        feed.setCreated(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));

        return feedmapper.toFeedFullDto(feedRepo.saveAndFlush(feed));
    }

    public FeedFullDto updatePost(Long userId, Long feedId, UpdateFeedDto dto) {
        Profile owner = checkIfProfileExist(userId);
        Feed feed = checkIfFeedExist(feedId);
        validateOwner(userId, feed);

        if (dto.getDescription() != null) {
            feed.setDescription(dto.getDescription());
        }
        if (dto.getOldFilesLinks() != null) {
            dto.getOldFilesLinks().forEach(this::deleteFile);
        }
        if (dto.getFiles() != null) {
            Set<FeedMedia> media = saveMedia(dto.getFiles(), owner.getUser().getEmail());
            feed.setMedia(media);
        }

        return feedmapper.toFeedFullDto(feedRepo.saveAndFlush(feed));
    }

    public List<FeedFullDto> getFeed(long id, Integer from, Integer size) {
        checkIfProfileExist(id);
        Pageable page = PageRequest.of(from, size, Sort.by(Sort.Direction.DESC, "id"));
        List<ProfilePreviewDtoResponse> subscriptions = profileService.getSubscriptions(id);
        List<Feed> feed = new ArrayList<>();
        subscriptions.stream()
                .map(sub -> (feedRepo.getLatestFeed(sub.getId(), page)))
                .forEach(feed::addAll);

        return feed.stream()
                .sorted(Comparator.comparing(Feed::getCreated).reversed())
                .map(feedmapper::toFeedFullDto)
                .collect(Collectors.toUnmodifiableList());
    }

    public FeedFullDto getById(Long id) {
        Feed feed = checkIfFeedExist(id);
        return feedmapper.toFeedFullDto(feed);
    }

    public void deleteById(Long ownerId, Long id) {
        Feed feed = checkIfFeedExist(id);
        checkIfProfileExist(ownerId);
        validateOwner(ownerId, feed);
        feed.getMedia().forEach(media -> deleteFile(media.getLink()));
        feedRepo.delete(feed);
    }

    public void addLike(Long userId, Long feedId) {
        Feed feed = checkIfFeedExist(feedId);
        checkIfProfileExist(userId);
        if (feed.getLikes().contains(userId)) {
            feed.getLikes().remove(userId);
        } else {
            feed.getLikes().add(userId);
        }
        feedRepo.saveAndFlush(feed);
    }

    private Set<FeedMedia> saveMedia(Set<MultipartFile> mediaFiles, String email) {
        if (mediaFiles == null) {
            return new HashSet<>();
        }
        return mediaFiles.stream()
                .map(file -> {
                    String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmss-"));
                    String fileName = file.getOriginalFilename();
                    String folderPath = "resources" + "/" + email + "/avatar";
                    Path path = Path.of(folderPath);
                    if (Files.notExists(path)) {
                        try {
                            Files.createDirectories(path);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    FeedMedia media = FeedMedia.builder()
                            .type(file.getContentType())
                            .title(date + fileName)
                            .link(folderPath + "/" + date + fileName)
                            .build();
                    try {
                        Files.copy(file.getInputStream(), Paths.get(folderPath + "/" + date + fileName));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return feedMediaRepo.saveAndFlush(media);
                })
                .collect(Collectors.toSet());
    }

    private void validateOwner(Long ownerId, Feed feed) {
        if (!feed.getOwner().getId().equals(ownerId)) {
            throw new ConflictException("User with profile id " + ownerId + "cannot update/delete this!");
        }
    }

    private Profile checkIfProfileExist(Long profileId) {
        return profileRepo.findById(profileId).orElseThrow(() ->
                new NotFoundValidationException("Profile for user with id " + profileId + " not found"));
    }

    private Feed checkIfFeedExist(Long feedId) {
        return feedRepo.findById(feedId).orElseThrow(() ->
                new NotFoundValidationException("Feed with id " + feedId + " not found"));
    }

    private void deleteFile(String path) {
        File fileToBeDeleted = new File(path);
        fileToBeDeleted.delete();
    }
}
