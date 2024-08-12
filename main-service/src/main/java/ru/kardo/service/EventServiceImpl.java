package ru.kardo.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.kardo.dto.event.EventDtoResponse;
import ru.kardo.dto.event.GrandFinalEventDtoResponse;
import ru.kardo.exception.NotFoundValidationException;
import ru.kardo.mapper.EventMapper;
import ru.kardo.mapper.GrandFinalEventMapper;
import ru.kardo.model.*;
import ru.kardo.model.enums.DirectionEnum;
import ru.kardo.model.enums.EventType;
import ru.kardo.repo.EventRepo;
import ru.kardo.repo.GrandFinalEventRepo;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService{

    private final EventRepo eventRepo;
    private final EventMapper eventMapper;
    private final GrandFinalEventMapper grandFinalEventMapper;
    private final GrandFinalEventRepo grandFinalEventRepo;

    @Override
    public List<EventDtoResponse> getAllEvents() {
        List<Event> eventList = eventRepo.findAll();
        return eventMapper.toEventDtoResponseList(eventList);
    }

    @Override
    public EventDtoResponse getEvent(Long eventId) {
        Event event = eventRepo.findById(eventId).orElseThrow(() ->
                new NotFoundValidationException("Event with id: " + eventId + " not found"));
        return eventMapper.toEventDtoResponse(event);
    }

    @Override
    public List<GrandFinalEventDtoResponse> getGrandFinalEvents(LocalDate date, String program, String direction,
                                                                Integer from, Integer size) {
        Pageable page = PageRequest.of(from, size, Sort.by("id").ascending());
        BooleanExpression expression = buildExpression(date, program, direction);
        List<GrandFinalEvent> eventList = grandFinalEventRepo.findAll(expression, page).getContent();
        if (eventList.isEmpty()) {
            throw new NotFoundValidationException("Nothing found");
        }
        return grandFinalEventMapper.toGrandFinalEventEventDtoResponseList(eventList);
    }

    private BooleanExpression buildExpression(LocalDate date, String program, String direction) {
        QGrandFinalEvent qGrandFinalEvent = QGrandFinalEvent.grandFinalEvent;
        BooleanExpression expression = qGrandFinalEvent.eq(qGrandFinalEvent);
        if (date != null) {
            expression = expression.and(qGrandFinalEvent.eventDate.eq(date));
        }
        if (direction != null) {
            expression = expression.and(qGrandFinalEvent.directionSet.contains(new Direction(DirectionEnum.valueOf(direction))));
        }
        if (program != null) {
            expression = expression.and(qGrandFinalEvent.eventType.eq(EventType.valueOf(program)));
        }
        return expression;
    }
}
