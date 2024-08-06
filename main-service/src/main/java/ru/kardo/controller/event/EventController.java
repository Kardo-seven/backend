package ru.kardo.controller.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/{eventId}")
    public ResponseEntity<EventDtoResponse> getEvent(@PathVariable Long eventId) {
        log.info("GET: /event/{}", eventId);
        return ResponseEntity.ok().body(eventService.getEvent(eventId));
    }
}
