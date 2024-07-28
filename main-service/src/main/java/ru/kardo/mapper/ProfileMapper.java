package ru.kardo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.kardo.dto.profile.ProfileFullDtoResponse;
import ru.kardo.dto.profile.ProfilePreviewDtoResponse;
import ru.kardo.dto.profile.ProfileShortDtoResponse;
import ru.kardo.model.Profile;

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
}
