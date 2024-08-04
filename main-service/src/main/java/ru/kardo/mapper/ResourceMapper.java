package ru.kardo.mapper;

import org.mapstruct.Mapper;
import ru.kardo.dto.resource.ResourceDto;
import ru.kardo.model.Resource;

@Mapper(componentModel = "spring", uses = Resource.class)
public interface ResourceMapper {
    ResourceDto toResourceDto(Resource res);
}
