package ru.kardo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.kardo.dto.AvatarDtoResponse;
import ru.kardo.model.Avatar;

@Mapper(componentModel = "spring")
public interface AvatarMapper {

    @Mapping(target = "profileId", source = "profile.id")
    AvatarDtoResponse toAvatarDtoResponse(Avatar avatar);
}
