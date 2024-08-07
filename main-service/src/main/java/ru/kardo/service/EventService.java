package ru.kardo.service;

import ru.kardo.dto.event.EventDtoResponse;

import java.time.LocalDate;
import java.util.List;

public interface EventService {

    List<EventDtoResponse> getAllEvents();

    EventDtoResponse getEvent(Long eventId);

    List<EventDtoResponse> getGrandFinalEvents(LocalDate date, String program, String direction, Integer from, Integer size);
}
