package ru.kardo.mapper;
import org.mapstruct.Mapper;
import ru.kardo.dto.event.GrandFinalEventDtoResponse;
import ru.kardo.model.GrandFinalEvent;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GrandFinalEventMapper {

    GrandFinalEventDtoResponse toGrandFinalEventDtoResponse(GrandFinalEvent grandFinalEvent);

    List<GrandFinalEventDtoResponse> toGrandFinalEventEventDtoResponseList(List<GrandFinalEvent> grandFinalEvents);
}
