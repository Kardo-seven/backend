package ru.kardo.mapper;

import org.mapstruct.*;
import ru.kardo.dto.request.UserRequestDtoRequest;
import ru.kardo.dto.request.UserRequestDtoResponse;
import ru.kardo.model.Direction;
import ru.kardo.model.Link;
import ru.kardo.model.UserRequest;

import java.util.HashSet;
import java.util.List;

@Mapper(componentModel = "spring", uses = ProfileMapper.class)
public interface UserRequestMapper {

    @Mapping(target = "eventId", source = "event.id")
    @Mapping(target = "profileId", source = "profile.id")
    @Mapping(target = "grandFinalEventId", source = "grandFinalEvent.id")
    @Mapping(target = "requestPreviewId", source = "requestPreview.id")
    UserRequestDtoResponse toUserRequestDtoResponse(UserRequest userRequest);

    @Mapping(target = "eventId", source = "event.id")
    @Mapping(target = "profileId", source = "profile.id")
    @Mapping(target = "grandFinalEventId", source = "grandFinalEvent.id")
    @Mapping(target = "requestPreviewId", source = "requestPreview.id")
    List<UserRequestDtoResponse> toUserRequestDtoResponseList(List<UserRequest> userRequestList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserRequestDtoResponse(UserRequestDtoRequest userRequestDtoRequest, @MappingTarget UserRequest userRequest);

    default void updateLinkList(UserRequest userRequest, UserRequestDtoRequest userRequestDtoRequest) {
        if (userRequestDtoRequest.getLinkList() != null) {
            if (!userRequestDtoRequest.getLinkList().isEmpty()) {
                userRequest.setLinkSet(new HashSet<>());
                userRequestDtoRequest.getLinkList().forEach(link -> userRequest.getLinkSet().add(new Link(link)));
            }
        }
    }

    default void updateDirectionList(UserRequest userRequest, UserRequestDtoRequest userRequestDtoRequest) {
        if (userRequestDtoRequest.getDirectionEnumList() != null) {
            if (!userRequestDtoRequest.getDirectionEnumList().isEmpty()) {
                userRequest.setDirectionSet(new HashSet<>());
                userRequestDtoRequest.getDirectionEnumList().forEach(directionEnum ->
                        userRequest.getDirectionSet().add(new Direction(directionEnum)));
            }
        }
    }
}
