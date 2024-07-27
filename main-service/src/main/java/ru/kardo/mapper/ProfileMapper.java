package ru.kardo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.kardo.dto.profile.ProfileDtoResponse;
import ru.kardo.model.Profile;

@Mapper(componentModel = "spring", uses = {UserMapper.class, PublicationMapper.class})
public interface ProfileMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "publicationDtoResponseList", source = "publicationList")
    ProfileDtoResponse toProfileDtoResponse(Profile profile);
}
