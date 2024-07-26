package ru.kardo.service;

import org.springframework.web.multipart.MultipartFile;
import ru.kardo.dto.AvatarDtoResponse;
import ru.kardo.dto.ProfileUpdateDtoRequest;
import ru.kardo.dto.ProfileUpdateDtoResponse;

import java.io.IOException;

public interface ProfileService {

    ProfileUpdateDtoResponse personalInformationUpdate(Long userId, ProfileUpdateDtoRequest profileUpdateDtoRequest);

    AvatarDtoResponse uploadAvatar(Long userId, MultipartFile multipartFile) throws IOException;

    AvatarDtoResponse getAvatar(Long userId);
}
