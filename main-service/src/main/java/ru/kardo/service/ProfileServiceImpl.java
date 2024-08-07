package ru.kardo.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepo profileRepo;
    private final ProfileMapper profileMapper;
    private final UserRepo userRepo;
    private final AvatarRepo avatarRepo;
    private final AvatarMapper avatarMapper;
    private final PublicationRepo publicationRepo;
    private final PublicationMapper publicationMapper;
    private final SubscriberRepo subscriberRepo;

    @Override
    public ProfileFullDtoResponse personalInformationUpdate(Long userId, ProfileUpdateDtoRequest profileUpdateDtoRequest) {
        Profile oldProfile = profileRepo.findById(userId).orElseThrow(() ->
                new NotFoundValidationException("Profile for user with id " + userId + " not found"));
        profileMapper.updateLinkList(oldProfile, profileUpdateDtoRequest);
        profileMapper.updateProfileDtoResponse(profileUpdateDtoRequest, oldProfile);
        profileRepo.save(oldProfile);
        return profileMapper.toProfileFullDtoResponse(oldProfile);
    }

    @Override
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
        List<Long> longs = subscriberRepo.getAllProfileSubscribers(profileId);
        List<Profile> profileList = longs.stream()
                .filter(Objects::nonNull)
                .map(id -> profileRepo.findById(id).orElseThrow(() ->
                        new NotFoundValidationException("Profile with id: " + profileId + " not found")))
                .collect(Collectors.toList());
        return profileMapper.toProfilePreviewDtoResponseList(profileList);
    }

    @Override
    public List<ProfilePreviewDtoResponse> getSubscriptions(Long profileId) {
        List<Long> longs = subscriberRepo.getAllProfileSubscriptions(profileId);
        List<Profile> profileList = longs.stream()
                .filter(Objects::nonNull)
                .map(id -> profileRepo.findById(id).orElseThrow(() ->
                        new NotFoundValidationException("Profile with id: " + profileId + " not found")))
                .collect(Collectors.toList());
        return profileMapper.toProfilePreviewDtoResponseList(profileList);
    }

//    @Override
//    public ProfileFullDtoResponse addSocialNetworkLink(Long userId, SocialNetworkDtoRequest socialNetworkDtoRequest) {
//        Profile profile = profileRepo.findById(userId).orElseThrow(() ->
//                new NotFoundValidationException("Profile for user with id: " + userId + " not found"));
//        socialNetworkDtoRequest.getLinkList().forEach(link -> profile.getLinkSet().add(new Link(link)));
//        profileRepo.save(profile);
//        return profileMapper.toProfileFullDtoResponse(profile);
//    }

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
    public void subscribe(Long subscriberId, Long profileId) {
        profileRepo.findById(profileId).orElseThrow(() ->
                new NotFoundValidationException("Profile with id: " + profileId + " not found"));
        profileRepo.findById(subscriberId).orElseThrow(() ->
                new NotFoundValidationException("Profile with id: " + subscriberId + " not found"));
        validateUsersById(subscriberId, profileId);
        Subscriber subscriber = Subscriber.builder()
                .userId(profileId)
                .subscriberId(subscriberId).build();
        subscriberRepo.save(subscriber);
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
        Pageable page = PageRequest.of(from / size, size, Sort.by(Sort.Direction.DESC, "id"));
        return profileRepo.findStaff(seasons, directions, mapAuthority(authorities), countries, page).stream()
                .map(profileMapper::toProfileAboutDto)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<ProfileAboutDto> getChildrenAndExperts() {
        return profileRepo.findAllByIsChildTrueOrIsChildExpertTrue().stream()
                .map(profileMapper::toProfileAboutDto)
                .collect(Collectors.toUnmodifiableList());
    }

    private void validateUsersById(Long subscriberId, Long profileId) {
        if (subscriberId.equals(profileId)) {
            throw new ConflictException("Subscriber id and profile id must not be the same");
        }
    }

    private Set<Authority> mapAuthority(Set<EnumAuth> list) {
        if (list == null) {
            return new HashSet<>();
        }
        return list.stream().map(Authority::new).collect(Collectors.toSet());
    }

//    private Profile profileParametersUpdate(Profile oldProfile, ProfileUpdateDtoRequest profileUpdateDtoRequest) {
////        if (profileUpdateDtoRequest.getName() != null) {
////            if (!profileUpdateDtoRequest.getName().isBlank()) {
////                oldProfile.setName(profileUpdateDtoRequest.getName());
////            }
////        }
////        if (profileUpdateDtoRequest.getLastName() != null) {
////            if (!profileUpdateDtoRequest.getLastName().isBlank()) {
////                oldProfile.setLastName(profileUpdateDtoRequest.getLastName());
////            }
////        }
////        if (profileUpdateDtoRequest.getSurName() != null) {
////            if (!profileUpdateDtoRequest.getSurName().isBlank()) {
////                oldProfile.setSurName(profileUpdateDtoRequest.getSurName());
////            }
////        }
////        if (profileUpdateDtoRequest.getPhone() != null) {
////            if (!profileUpdateDtoRequest.getPhone().isBlank()) {
////                oldProfile.setPhone(profileUpdateDtoRequest.getPhone());
////            }
////        }
////        if (profileUpdateDtoRequest.getBirthday() != null) {
////            if (!profileUpdateDtoRequest.getBirthday().isAfter(ChronoLocalDate.from(LocalDateTime.now()))) {
////                oldProfile.setBirthday(profileUpdateDtoRequest.getBirthday());
////            }
////        }
////        if (profileUpdateDtoRequest.getGender() != null) {
////                oldProfile.setGender(profileUpdateDtoRequest.getGender());
////        }
////        if (profileUpdateDtoRequest.getCountry() != null) {
////            if (!profileUpdateDtoRequest.getCountry().isBlank()) {
////                oldProfile.setCountry(profileUpdateDtoRequest.getCountry());
////            }
////        }
////        if (profileUpdateDtoRequest.getRegion() != null) {
////            if (!profileUpdateDtoRequest.getRegion().isBlank()) {
////                oldProfile.setRegion(profileUpdateDtoRequest.getRegion());
////            }
////        }
////        if (profileUpdateDtoRequest.getCity() != null) {
////            if (!profileUpdateDtoRequest.getCity().isBlank()) {
////                oldProfile.setCity(profileUpdateDtoRequest.getCity());
////            }
////        }
////        if (profileUpdateDtoRequest.getCitizenship() != null) {
////            if (!profileUpdateDtoRequest.getCitizenship().isBlank()) {
////                oldProfile.setCitizenship(profileUpdateDtoRequest.getCitizenship());
////            }
////        }
//        if (profileUpdateDtoRequest.getLinkList() != null) {
//            if (!profileUpdateDtoRequest.getLinkList().isEmpty()) {
//                oldProfile.setLinkSet(new HashSet<>());
//                profileUpdateDtoRequest.getLinkList().forEach(link -> oldProfile.getLinkSet().add(new Link(link)));
//            }
//        }
//        return oldProfile;
//    }
}
