package ru.kardo.mapper;

import org.mapstruct.*;
import ru.kardo.dto.profile.ProfileAboutDto;
import ru.kardo.dto.profile.ProfileFullDtoResponse;
import ru.kardo.dto.profile.ProfilePreviewDtoResponse;
import ru.kardo.dto.profile.ProfileShortDtoResponse;
import ru.kardo.dto.profile.ProfileUpdateDtoRequest;
import ru.kardo.model.Link;
import ru.kardo.model.Profile;
import java.util.HashSet;
import java.util.List;


@Mapper(componentModel = "spring", uses = {UserMapper.class, PublicationMapper.class,
AvatarMapper.class})
public interface ProfileMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "avatarId", source = "avatar.id")
    @Mapping(target = "publicationDtoResponseList", source = "publicationList")
    ProfileFullDtoResponse toProfileFullDtoResponse(Profile profile);

    @Mapping(target = "avatarLink", source = "avatar.link")
    @Mapping(target = "publicationDtoResponseList", source = "publicationList")
    ProfileShortDtoResponse toProfileShortDtoResponse(Profile profile);

    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "avatarLink", source = "avatar.link")
    ProfilePreviewDtoResponse toProfilePreviewDtoResponse(Profile profile);

    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "avatarLink", source = "avatar.link")
    List<ProfilePreviewDtoResponse> toProfilePreviewDtoResponseList(List<Profile> profileList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProfileDtoResponse(ProfileUpdateDtoRequest profileUpdateDtoRequest, @MappingTarget Profile profile);

    default void updateLinkList(Profile profile, ProfileUpdateDtoRequest profileUpdateDtoRequest) {
        if (profileUpdateDtoRequest.getLinkList() != null) {
            if (!profileUpdateDtoRequest.getLinkList().isEmpty()) {
                profile.setLinkSet(new HashSet<>());
                profileUpdateDtoRequest.getLinkList().forEach(link -> profile.getLinkSet().add(new Link(link)));
            }
        }
    }

    @Mapping(target = "avatarLink", source = "avatar.link")
    ProfileAboutDto toProfileAboutDto(Profile profile);
}
