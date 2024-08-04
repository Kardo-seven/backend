package ru.kardo.service;

import ru.kardo.dto.event.EventDtoResponse;

import java.util.List;

public interface EventService {

    List<EventDtoResponse> getAllEvents();
}
