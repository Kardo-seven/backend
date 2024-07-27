package ru.kardo.service;

import org.springframework.web.multipart.MultipartFile;
import ru.kardo.dto.profile.*;

import java.io.IOException;
import java.util.List;

public interface ProfileService {

    ProfileDtoResponse personalInformationUpdate(Long userId, ProfileUpdateDtoRequest profileUpdateDtoRequest);

    AvatarDtoResponse uploadAvatar(Long userId, MultipartFile multipartFile) throws IOException;

    PublicationDtoResponse uploadPublication(Long userId, MultipartFile multipartFile, String description) throws IOException;

    AvatarDtoResponse getAvatar(Long profileId);

    List<PublicationDtoResponse> getPublications(Long profileId);

    List<ProfileShortDtoResponse> getSubscribers(Long profileId);

    ProfileDtoResponse addSocialNetworkLink(Long userId, SocialNetworkDtoRequest socialNetworkDtoRequest);

    ProfileDtoResponse getProfile(Long profileId);

    PublicationDtoResponse getPublication(Long publicationId, Long profileId);

    void subscribe(Long subscriberId, Long profileId);
}
