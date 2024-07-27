package ru.kardo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.kardo.dto.profile.ProfileDtoResponse;
import ru.kardo.dto.profile.ProfileShortDtoResponse;
import ru.kardo.model.Profile;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class, PublicationMapper.class})
public interface ProfileMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "publicationDtoResponseList", source = "publicationList")
    ProfileDtoResponse toProfileDtoResponse(Profile profile);

    @Mapping(target = "email", source = "user.email")
    ProfileShortDtoResponse toProfileShortDtoResponse(Profile profile);

    @Mapping(target = "email", source = "user.email")
    List<ProfileShortDtoResponse> toProfileShortDtoResponseList(List<Profile> profileList);
}
