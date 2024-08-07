package ru.kardo.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.kardo.dto.event.EventDtoResponse;
import ru.kardo.exception.NotFoundValidationException;
import ru.kardo.mapper.EventMapper;
import ru.kardo.model.Direction;
import ru.kardo.model.Event;
import ru.kardo.model.QEvent;
import ru.kardo.model.enums.DirectionEnum;
import ru.kardo.model.enums.EventType;
import ru.kardo.repo.EventRepo;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService{

    private final EventRepo eventRepo;
    private final EventMapper eventMapper;

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
    public List<EventDtoResponse> getGrandFinalEvents(LocalDate date, String program, String direction,
                                                      Integer from, Integer size) {
        Pageable page = PageRequest.of(from, size, Sort.by("id").ascending());
        BooleanExpression expression = buildExpression(date, program, direction);
        List<Event> eventList = eventRepo.findAll(expression, page).getContent()
                .stream()
                .filter(Event::getIsGrandFinalEvent).toList();
        if (eventList.isEmpty()) {
            eventList = eventRepo.findAllByIsGrandFinalEventTrue(page);
        }
        return eventMapper.toEventDtoResponseList(eventList);
    }

    private BooleanExpression buildExpression(LocalDate date, String program, String direction) {
        QEvent qEvent = QEvent.event;
        BooleanExpression expression = qEvent.eq(qEvent);
        if (date != null) {
            expression = expression.and(qEvent.eventDate.eq(date));
        }
        if (direction != null) {
            expression = expression.and(qEvent.directionSet.contains(new Direction(DirectionEnum.valueOf(direction))));
        }
        if (program != null) {
            expression = expression.and(qEvent.eventType.eq(EventType.valueOf(program)));
        }
        return expression;
    }
}
