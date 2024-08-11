package ru.kardo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.kardo.dto.feed.CreateFeedDto;
import ru.kardo.dto.feed.UpdateFeedDto;
import ru.kardo.exception.ConflictException;
import ru.kardo.exception.NotFoundValidationException;
import ru.kardo.mapper.FeedMapper;
import ru.kardo.model.Feed;
import ru.kardo.model.FeedMedia;
import ru.kardo.model.Profile;
import ru.kardo.repo.FeedMediaRepo;
import ru.kardo.repo.FeedRepo;
import ru.kardo.repo.ProfileRepo;
import ru.kardo.repo.UserRepo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityFeedServiceImpl {

    private final FeedRepo feedRepo;
    private final FeedMediaRepo feedMediaRepo;
    private final UserRepo userRepo;
    private final ProfileRepo profileRepo;
    private final ProfileService profileService;

    private final FeedMapper feedmapper;

    public void addNewPost(Long userId, CreateFeedDto dto) {
        Profile owner = checkIfProfileExist(userId);

        Set<FeedMedia> media = parseMedia(dto.getMedia(), owner.getUser().getEmail(), false);

        Feed feed = new Feed();
        feed.setOwner(owner);
        feed.setMedia(media);
        feed.setDescription(dto.getDescription());
        feed.setCreated(LocalDateTime.now());

        feedmapper.toFeedFullDto(feedRepo.saveAndFlush(feed));
    }

    public void updatePost(Long userId, Long feedId, UpdateFeedDto dto) {
        Profile owner = checkIfProfileExist(userId);
        Feed feed = checkIfFeedExist(feedId);
        validateOwner(userId, feed);

        if (dto.getDescription() != null) {
            feed.setDescription(dto.getDescription());
        }
        if (dto.getMedia() != null) {
            Set<FeedMedia> media = parseMedia(dto.getMedia(), owner.getUser().getEmail(), true);
            feed.setMedia(media);
        }

        feedmapper.toFeedFullDto(feedRepo.saveAndFlush(feed));
    }

    private Set<FeedMedia> parseMedia(Set<MultipartFile> mediaFiles, String email, boolean isUpdate) {
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

                    if (isUpdate) {
                        /**
                         * TO DO
                         * реализовать логику удаления старого файла при обновлении
                         */
                    }
                    return feedMediaRepo.saveAndFlush(media);
                })
                .collect(Collectors.toSet());
    }

    private void validateOwner(Long ownerId, Feed feed) {
        if (!feed.getOwner().getId().equals(ownerId)) {
            throw new ConflictException("User with profile id " + ownerId + "can not update this!");
        }
    }

    private Profile checkIfProfileExist(Long profileId){
        return profileRepo.findById(profileId).orElseThrow(() ->
                new NotFoundValidationException("Profile for user with id " + profileId + " not found"));
    }
    private Feed checkIfFeedExist(Long feedId){
        return feedRepo.findById(feedId).orElseThrow(() ->
                new NotFoundValidationException("Feed with id " + feedId + " not found"));
    }

}
