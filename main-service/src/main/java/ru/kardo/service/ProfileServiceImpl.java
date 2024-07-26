package ru.kardo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.kardo.dto.AvatarDtoResponse;
import ru.kardo.dto.ProfileUpdateDtoRequest;
import ru.kardo.dto.ProfileUpdateDtoResponse;
import ru.kardo.exception.NotFoundValidationException;
import ru.kardo.mapper.AvatarMapper;
import ru.kardo.mapper.ProfileMapper;
import ru.kardo.model.Avatar;
import ru.kardo.model.Profile;
import ru.kardo.model.User;
import ru.kardo.repo.AvatarRepo;
import ru.kardo.repo.ProfileRepo;
import ru.kardo.repo.UserRepo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepo profileRepo;
    private final ProfileMapper profileMapper;
    private final UserRepo userRepo;
    private final AvatarRepo avatarRepo;
    private final AvatarMapper avatarMapper;

    @Override
    public ProfileUpdateDtoResponse personalInformationUpdate(Long userId, ProfileUpdateDtoRequest profileUpdateDtoRequest) {
       Profile oldProfile = profileRepo.findById(userId).orElseThrow(() ->
                new NotFoundValidationException("Profile for user with id " + userId + " not found"));
       Profile updatedProfile = profileParametersUpdate(oldProfile, profileUpdateDtoRequest);
       profileRepo.save(updatedProfile);
       return profileMapper.toProfileUpdateDtoResponse(updatedProfile);
    }

    @Override
    public AvatarDtoResponse uploadAvatar(Long userId, MultipartFile multipartFile) throws IOException {
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

    @Override
    public AvatarDtoResponse getAvatar(Long userId) {
        Avatar avatar = avatarRepo.findAvatarByProfileId(userId).orElseThrow(() ->
                new NotFoundValidationException("Avatar for profile with id " + userId + " not found"));
       return avatarMapper.toAvatarDtoResponse(avatar);
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
