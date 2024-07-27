package ru.kardo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.kardo.dto.profile.PublicationDtoResponse;
import ru.kardo.model.Publication;

import java.util.List;

@Mapper(componentModel = "spring", uses = ProfileMapper.class)
public interface PublicationMapper {

    @Mapping(target = "profileId", source = "profile.id")
    PublicationDtoResponse toPublicationDtoResponse(Publication publication);

    @Mapping(target = "profileId", source = "profile.id")
    List<PublicationDtoResponse> toPublicationDtoResponseList(List<Publication> publicationList);
}
