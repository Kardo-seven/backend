package ru.kardo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.kardo.dto.request.UserRequestDtoResponse;
import ru.kardo.model.UserRequest;

@Mapper(componentModel = "spring")
public interface UserRequestMapper {

    @Mapping(target = "eventId", source = "event.id")
    @Mapping(target = "profileId", source = "profile.id")
    @Mapping(target = "requestPreviewId", source = "requestPreview.id")
    UserRequestDtoResponse toUserRequestDtoResponse(UserRequest userRequest);
}
