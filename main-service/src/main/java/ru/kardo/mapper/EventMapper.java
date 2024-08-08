package ru.kardo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.kardo.dto.event.EventDtoResponse;
import ru.kardo.model.Event;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {

    @Mapping(target = "eventImageDtoResponse", source = "eventImage")
    EventDtoResponse toEventDtoResponse(Event event);

    @Mapping(target = "eventImageDtoResponse", source = "eventImage")
    List<EventDtoResponse> toEventDtoResponseList(List<Event> events);
}
