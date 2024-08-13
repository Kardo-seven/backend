package ru.kardo.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.kardo.dto.profile.*;
import ru.kardo.exception.ConflictException;
import ru.kardo.exception.NotFoundValidationException;
import ru.kardo.mapper.AvatarMapper;
import ru.kardo.mapper.ProfileMapper;
import ru.kardo.mapper.PublicationMapper;
import ru.kardo.model.*;
import ru.kardo.model.enums.DirectionEnum;
import ru.kardo.model.enums.EnumAuth;
import ru.kardo.repo.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepo profileRepo;
    private final ProfileMapper profileMapper;
    private final AvatarRepo avatarRepo;
    private final AvatarMapper avatarMapper;
    private final PublicationRepo publicationRepo;
    private final PublicationMapper publicationMapper;
    private final SubscriptionRepo subscriptionRepo;

    @Override
    @Transactional
    public ProfileFullDtoResponse personalInformationUpdate(Long userId, ProfileUpdateDtoRequest profileUpdateDtoRequest) {
        Profile oldProfile = profileRepo.findById(userId).orElseThrow(() ->
                new NotFoundValidationException("Profile for user with id " + userId + " not found"));
        profileMapper.updateLinkList(oldProfile, profileUpdateDtoRequest);
        profileMapper.updateProfileDtoResponse(profileUpdateDtoRequest, oldProfile);
        profileRepo.save(oldProfile);
        return profileMapper.toProfileFullDtoResponse(oldProfile);
    }

    @Override
    @Transactional
    public AvatarDtoResponse uploadAvatar(Long userId, MultipartFile multipartFile) throws IOException {
        Profile profile = profileRepo.findById(userId).orElseThrow(() ->
                new NotFoundValidationException("Profile with id: " + userId + " not found"));
        profileAvatarValidation(profile);
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmss-"));
        String fileName = multipartFile.getOriginalFilename();
        String folderPath = "resources" + "/" + profile.getUser().getEmail() + "/avatar";
        Path path = Path.of(folderPath);
        if (Files.notExists(path)) {
            Files.createDirectories(path);
        }
        Avatar avatar = Avatar.builder()
                .type(multipartFile.getContentType())
                .title(date + fileName)
                .link(folderPath + "/" + date + fileName)
                .build();
        profile.setAvatar(avatar);
        avatarRepo.save(avatar);
        profileRepo.save(profile);
        Files.copy(multipartFile.getInputStream(), Paths.get(folderPath + "/" + date + fileName));
        return avatarMapper.toAvatarDtoResponse(avatar);
    }

    private void profileAvatarValidation(Profile profile) {
        if (profile.getAvatar() != null) {
            throw new ConflictException("User already have avatar");
        }
    }

    @Override
    @Transactional
    public PublicationDtoResponse uploadPublication(Long userId, MultipartFile multipartFile,
                                                    String description) throws IOException {
        Profile profile = profileRepo.findById(userId).orElseThrow(() ->
                new NotFoundValidationException("Profile with id: " + userId + " not found"));
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmss-"));
        String fileName = multipartFile.getOriginalFilename();
        String folderPath = "resources" + "/" + profile.getUser().getEmail() + "/publications";
        Path path = Path.of(folderPath);
        if (Files.notExists(path)) {
            Files.createDirectories(path);
        }
        Publication publication = Publication.builder()
                .profile(profile)
                .description(description)
                .type(multipartFile.getContentType())
                .title(date + fileName)
                .link(folderPath + "/" + date + fileName)
                .build();
        publicationRepo.save(publication);
        Files.copy(multipartFile.getInputStream(), Paths.get(folderPath + "/" + date + fileName));
        return publicationMapper.toPublicationDtoResponse(publication);
    }

    @Override
    public AvatarDtoResponse getAvatar(Long avatarId) {
        Avatar avatar = avatarRepo.findById(avatarId).orElseThrow(() ->
                new NotFoundValidationException("Avatar with id: " + avatarId + " not found"));
       return avatarMapper.toAvatarDtoResponse(avatar);
    }

    @Override
    public List<PublicationDtoResponse> getPublications(Long profileId) {
        List<Publication> publications = publicationRepo.findAllByProfileId(profileId);
        return publicationMapper.toPublicationDtoResponseList(publications);
    }

    @Override
    public List<ProfilePreviewDtoResponse> getSubscribers(Long profileId) {
        List<Long> longs = subscriptionRepo.getAllProfileSubscribers(profileId);
        List<Profile> profileList = longs.stream()
                .filter(Objects::nonNull)
                .map(id -> profileRepo.findById(id).orElseThrow(() ->
                        new NotFoundValidationException("Profile with id: " + profileId + " not found")))
                .collect(Collectors.toList());
        return profileMapper.toProfilePreviewDtoResponseList(profileList);
    }

    @Override
    public List<ProfilePreviewDtoResponse> getSubscriptions(Long profileId) {
        List<Long> longs = subscriptionRepo.getAllProfileSubscriptions(profileId);
        List<Profile> profileList = longs.stream()
                .filter(Objects::nonNull)
                .map(id -> profileRepo.findById(id).orElseThrow(() ->
                        new NotFoundValidationException("Profile with id: " + profileId + " not found")))
                .collect(Collectors.toList());
        return profileMapper.toProfilePreviewDtoResponseList(profileList);
    }

    @Override
    public ProfileFullDtoResponse getOwnProfile(Long profileId) {
        Profile profile = profileRepo.findById(profileId).orElseThrow(() ->
                new NotFoundValidationException("Profile for user with id: " + profileId + " not found"));
        return profileMapper.toProfileFullDtoResponse(profile);
    }

    @Override
    public ProfileShortDtoResponse getProfile(Long profileId) {
        Profile profile = profileRepo.findById(profileId).orElseThrow(() ->
                new NotFoundValidationException("Profile for user with id: " + profileId + " not found"));
        return profileMapper.toProfileShortDtoResponse(profile);
    }

    @Override
    public PublicationDtoResponse getPublication(Long publicationId, Long profileId) {
        Publication publication = publicationRepo.findByIdAndProfileId(publicationId, profileId).orElseThrow(() ->
                new NotFoundValidationException("Publication for user with id: " + publicationId +
                        " for profile with id: " + profileId + " not found"));
        return publicationMapper.toPublicationDtoResponse(publication);
    }

    @Override
    @Transactional
    public void subscribe(Long subscriberId, Long profileId) {
        profileRepo.findById(profileId).orElseThrow(() ->
                new NotFoundValidationException("Profile with id: " + profileId + " not found"));
        profileRepo.findById(subscriberId).orElseThrow(() ->
                new NotFoundValidationException("Profile with id: " + subscriberId + " not found"));
        validateSubscription(subscriberId, profileId);
        Subscription subscription = Subscription.builder()
                .userId(profileId)
                .subscriberId(subscriberId).build();
        subscriptionRepo.save(subscription);
    }

    private void validateSubscription(Long subscriberId, Long profileId) {
        if (subscriberId.equals(profileId)) {
            throw new ConflictException("Subscriber id and profile id must not be the same");
        }
        if (subscriptionRepo.getSubscriberBySubscriberIdAndUserId(subscriberId, profileId).isPresent()) {
            throw new ConflictException("You already subscribe on this profile");
        }
    }

    @Override
    public List<ProfilePreviewDtoResponse> getProfiles(Integer from, Integer size) {
        List<Profile> profileList = profileRepo.findAll(PageRequest.of(from, size, Sort.by("name").ascending())).toList();
        return profileMapper.toProfilePreviewDtoResponseList(profileList);
    }

    @Override
    public List<ProfileAboutDto> getStaffAndFacts(Set<String> seasons, Set<DirectionEnum> directions,
                                                  Set<EnumAuth> authorities, Set<String> countries,
                                                  Integer from, Integer size) {
        Pageable page = PageRequest.of(from, size, Sort.by(Sort.Direction.DESC, "id"));
        BooleanExpression filters = buildExpression(seasons, directions, authorities, countries, false);
        return profileRepo.findAll(filters, page).getContent().stream()
                .map(profileMapper::toProfileAboutDto)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<ProfileAboutDto> getChildrenAndExperts(Set<String> seasons, Set<DirectionEnum> directions,
                                                       Set<EnumAuth> authorities, Set<String> countries,
                                                       Integer from, Integer size) {
        Pageable page = PageRequest.of(from, size, Sort.by(Sort.Direction.DESC, "id"));
        BooleanExpression filters = buildExpression(seasons, directions, authorities, countries, true);
        return profileRepo.findAll(filters, page).getContent().stream()
                .map(profileMapper::toProfileAboutDto)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Long getStaffCount() {
        List<Profile> profileList = profileRepo.findAllByIsChildFalseAndIsChildExpertFalse();
        return (long) profileList.size();
    }

    @Override
    public Long getKidsAndStaffCount() {
        List<Profile> profileList = profileRepo.findAllByIsChildTrueOrIsChildExpertTrue();
        return (long) profileList.size();
    }

    private BooleanExpression buildExpression(Set<String> seasons, Set<DirectionEnum> directions,
                                              Set<EnumAuth> authorities, Set<String> countries, boolean isChildrenAdExperts) {
        QProfile qProfile = QProfile.profile;
        BooleanExpression expression = qProfile.eq(qProfile);
        if (seasons != null) {
            expression = expression.and(qProfile.seasons.any().in(seasons));
        }
        if (directions != null) {
            expression = expression.and(qProfile.directions.any().in(directions));
        }
        if (authorities != null) {
            expression = expression.and(qProfile.user.authoritySet.any().in(
                    authorities.stream().map(Authority::new).collect(Collectors.toSet()))
            );
        }
        if (countries != null) {
            expression = expression.and(qProfile.country.in(countries));
        }
        if (isChildrenAdExperts) {
            expression = expression.andAnyOf(qProfile.isChild.isTrue(), qProfile.isChildExpert.isTrue());
        }
        return expression;
    }
}
