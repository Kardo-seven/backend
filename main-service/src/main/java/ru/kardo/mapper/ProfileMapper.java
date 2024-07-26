package ru.kardo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.kardo.dto.ProfileUpdateDtoResponse;
import ru.kardo.model.Profile;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface ProfileMapper {

    @Mapping(target = "userId", source = "user.id")
    ProfileUpdateDtoResponse toProfileUpdateDtoResponse(Profile profile);
}
