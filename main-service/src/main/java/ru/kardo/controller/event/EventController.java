package ru.kardo.controller.event;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kardo.dto.event.EventDtoResponse;
import ru.kardo.service.EventService;

import java.util.List;

@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
@Slf4j
public class EventController {

    private final EventService eventService;

    @GetMapping
    public ResponseEntity<List<EventDtoResponse>> getAllEvents() {
        log.info("GET: /event");
        return ResponseEntity.ok().body(eventService.getAllEvents());
    }
}
