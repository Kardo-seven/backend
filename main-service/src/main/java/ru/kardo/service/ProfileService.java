package ru.kardo.service;

import org.springframework.web.multipart.MultipartFile;
import ru.kardo.dto.profile.*;

import java.io.IOException;
import java.util.List;

public interface ProfileService {

    ProfileFullDtoResponse personalInformationUpdate(Long userId, ProfileUpdateDtoRequest profileUpdateDtoRequest);

    AvatarDtoResponse uploadAvatar(Long userId, MultipartFile multipartFile) throws IOException;

    PublicationDtoResponse uploadPublication(Long userId, MultipartFile multipartFile, String description) throws IOException;

    AvatarDtoResponse getAvatar(Long avatarId);

    List<PublicationDtoResponse> getPublications(Long profileId);

    List<ProfilePreviewDtoResponse> getSubscribers(Long profileId);

    List<ProfilePreviewDtoResponse> getSubscriptions(Long profileId);

//    ProfileFullDtoResponse addSocialNetworkLink(Long userId, SocialNetworkDtoRequest socialNetworkDtoRequest);

    ProfileFullDtoResponse getOwnProfile(Long profileId);

    ProfileShortDtoResponse getProfile(Long profileId);

    PublicationDtoResponse getPublication(Long publicationId, Long profileId);

    void subscribe(Long subscriberId, Long profileId);

    List<ProfilePreviewDtoResponse> getProfiles(Integer from, Integer size);
}
