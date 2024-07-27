package ru.kardo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.kardo.dto.profile.*;
import ru.kardo.exception.ConflictException;
import ru.kardo.exception.NotFoundValidationException;
import ru.kardo.mapper.AvatarMapper;
import ru.kardo.mapper.ProfileMapper;
import ru.kardo.mapper.PublicationMapper;
import ru.kardo.model.*;
import ru.kardo.repo.AvatarRepo;
import ru.kardo.repo.ProfileRepo;
import ru.kardo.repo.PublicationRepo;
import ru.kardo.repo.UserRepo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Override
    public ProfileDtoResponse personalInformationUpdate(Long userId, ProfileUpdateDtoRequest profileUpdateDtoRequest) {
       Profile oldProfile = profileRepo.findById(userId).orElseThrow(() ->
                new NotFoundValidationException("Profile for user with id " + userId + " not found"));
       Profile updatedProfile = profileParametersUpdate(oldProfile, profileUpdateDtoRequest);
       profileRepo.save(updatedProfile);
       return profileMapper.toProfileDtoResponse(updatedProfile);
    }

    @Override
    public AvatarDtoResponse uploadAvatar(Long userId, MultipartFile multipartFile) throws IOException {
        userIdValidation(userId);
        User user = userRepo.findById(userId).orElseThrow(() ->
                new NotFoundValidationException("User with id " + userId + " not found"));
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmss-"));
        String fileName = multipartFile.getOriginalFilename();
        String folderPath = "resources" + "/" + user.getEmail() + "/avatar";
        Path path = Path.of(folderPath);
        if (Files.notExists(path)) {
            Files.createDirectories(path);
        }
        Avatar avatar = Avatar.builder()
                .profile(profileRepo.findProfileByUserId(user.getId()))
                .type(multipartFile.getContentType())
                .title(date + fileName)
                .link(folderPath + "/" + date + fileName)
                .build();
        avatarRepo.save(avatar);
        Files.copy(multipartFile.getInputStream(), Paths.get(folderPath + "/" + date + fileName));
        return avatarMapper.toAvatarDtoResponse(avatar);
    }

    private void userIdValidation(Long id) {
        Set<Long> longSet = new HashSet<>(avatarRepo.findAllIds());
        if (longSet.contains(id)) {
            throw new ConflictException("User already have avatar");
        }
    }

    @Override
    public PublicationDtoResponse uploadPublication(Long userId, MultipartFile multipartFile,
                                                    String description) throws IOException {
        User user = userRepo.findById(userId).orElseThrow(() ->
                new NotFoundValidationException("User with id " + userId + " not found"));
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmss-"));
        String fileName = multipartFile.getOriginalFilename();
        String folderPath = "resources" + "/" + user.getEmail() + "/publications";
        Path path = Path.of(folderPath);
        if (Files.notExists(path)) {
            Files.createDirectories(path);
        }
        Publication publication = Publication.builder()
                .profile(profileRepo.findProfileByUserId(user.getId()))
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
    public AvatarDtoResponse getAvatar(Long userId) {
        Avatar avatar = avatarRepo.findAvatarByProfileId(userId).orElseThrow(() ->
                new NotFoundValidationException("Avatar for profile with id " + userId + " not found"));
       return avatarMapper.toAvatarDtoResponse(avatar);
    }

    @Override
    public List<PublicationDtoResponse> getPublications(Long userId) {
        List<Publication> publications = publicationRepo.findAllByProfileId(userId);
        return publicationMapper.toPublicationDtoResponseList(publications);
    }

    @Override
    public ProfileDtoResponse addSocialNetworkLink(Long userId, SocialNetworkDtoRequest socialNetworkDtoRequest) {
        Profile profile = profileRepo.findById(userId).orElseThrow(() ->
                new NotFoundValidationException("Profile for user with id: " + userId + " not found"));
        socialNetworkDtoRequest.getLinkList().forEach(link -> profile.getLinkSet().add(new Link(link)));
        profileRepo.save(profile);
        return profileMapper.toProfileDtoResponse(profile);
    }

    @Override
    public ProfileDtoResponse getProfile(Long userId) {
        Profile profile = profileRepo.findById(userId).orElseThrow(() ->
                new NotFoundValidationException("Profile for user with id: " + userId + " not found"));
        return profileMapper.toProfileDtoResponse(profile);
    }

    @Override
    public PublicationDtoResponse getPublication(Long publicationId, Long userId) {
        Publication publication = publicationRepo.findByIdAndProfileId(publicationId, userId).orElseThrow(() ->
                new NotFoundValidationException("Publication for user with id: " + publicationId +
                        " for user with id: " + userId + " not found"));
        return publicationMapper.toPublicationDtoResponse(publication);
    }

    private Profile profileParametersUpdate(Profile oldProfile, ProfileUpdateDtoRequest profileUpdateDtoRequest) {
        if (profileUpdateDtoRequest.getName() != null) {
            if (!profileUpdateDtoRequest.getName().isBlank()) {
                oldProfile.setName(profileUpdateDtoRequest.getName());
            }
        }
        if (profileUpdateDtoRequest.getLastName() != null) {
            if (!profileUpdateDtoRequest.getLastName().isBlank()) {
                oldProfile.setLastName(profileUpdateDtoRequest.getLastName());
            }
        }
        if (profileUpdateDtoRequest.getSurName() != null) {
            if (!profileUpdateDtoRequest.getSurName().isBlank()) {
                oldProfile.setSurName(profileUpdateDtoRequest.getSurName());
            }
        }
        if (profileUpdateDtoRequest.getPhone() != null) {
            if (!profileUpdateDtoRequest.getPhone().isBlank()) {
                oldProfile.setPhone(profileUpdateDtoRequest.getPhone());
            }
        }
        if (profileUpdateDtoRequest.getBirthday() != null) {
            if (!profileUpdateDtoRequest.getBirthday().isAfter(ChronoLocalDate.from(LocalDateTime.now()))) {
                oldProfile.setBirthday(profileUpdateDtoRequest.getBirthday());
            }
        }
        if (profileUpdateDtoRequest.getGender() != null) {
                oldProfile.setGender(profileUpdateDtoRequest.getGender());
        }
        if (profileUpdateDtoRequest.getCountry() != null) {
            if (!profileUpdateDtoRequest.getCountry().isBlank()) {
                oldProfile.setCountry(profileUpdateDtoRequest.getCountry());
            }
        }
        if (profileUpdateDtoRequest.getRegion() != null) {
            if (!profileUpdateDtoRequest.getRegion().isBlank()) {
                oldProfile.setRegion(profileUpdateDtoRequest.getRegion());
            }
        }
        if (profileUpdateDtoRequest.getCity() != null) {
            if (!profileUpdateDtoRequest.getCity().isBlank()) {
                oldProfile.setCity(profileUpdateDtoRequest.getCity());
            }
        }
        if (profileUpdateDtoRequest.getCitizenship() != null) {
            if (!profileUpdateDtoRequest.getCitizenship().isBlank()) {
                oldProfile.setCitizenship(profileUpdateDtoRequest.getCitizenship());
            }
        }
        return oldProfile;
    }
}
