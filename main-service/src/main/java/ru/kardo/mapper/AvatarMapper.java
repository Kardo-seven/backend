package ru.kardo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.kardo.dto.profile.AvatarDtoResponse;
import ru.kardo.model.Avatar;

@Mapper(componentModel = "spring", uses = ProfileMapper.class)
public interface AvatarMapper {

    @Mapping(target = "profileId", source = "profile.id")
    AvatarDtoResponse toAvatarDtoResponse(Avatar avatar);
}
