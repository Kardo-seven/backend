package ru.kardo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kardo.dto.event.EventDtoResponse;
import ru.kardo.mapper.EventMapper;
import ru.kardo.model.Event;
import ru.kardo.repo.EventRepo;

import java.util.ArrayList;
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
}
