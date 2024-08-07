package ru.kardo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.kardo.dto.event.EventDtoResponse;
import ru.kardo.model.Event;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {

    EventDtoResponse toEventDtoResponse(Event event);

    List<EventDtoResponse> toEventDtoResponseList(List<Event> events);
}
