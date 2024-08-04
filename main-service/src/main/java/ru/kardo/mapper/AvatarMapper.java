package ru.kardo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.kardo.dto.profile.AvatarDtoResponse;
import ru.kardo.model.Avatar;

@Mapper(componentModel = "spring")
public interface AvatarMapper {

    AvatarDtoResponse toAvatarDtoResponse(Avatar avatar);
}
