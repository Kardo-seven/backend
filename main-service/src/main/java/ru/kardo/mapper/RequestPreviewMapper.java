package ru.kardo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.kardo.dto.request.RequestPreviewDtoResponse;
import ru.kardo.model.RequestPreview;

@Mapper(componentModel = "spring", uses = {ProfileMapper.class, UserRequestMapper.class})
public interface RequestPreviewMapper {

    @Mapping(target = "userRequestId", source = "userRequest.id")
    RequestPreviewDtoResponse toRequestPreviewDtoResponse(RequestPreview requestPreview);
}
