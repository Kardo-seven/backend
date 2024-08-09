package ru.kardo.controller.event;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kardo.dto.event.EventDtoResponse;
import ru.kardo.dto.event.GrandFinalEventDtoResponse;
import ru.kardo.service.EventService;

import java.time.LocalDate;
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

    @GetMapping("/program")
    public ResponseEntity<List<GrandFinalEventDtoResponse>> getGrandFinalEvents(@RequestParam(required = false) LocalDate date,
                                                                                @RequestParam(required = false) String program,
                                                                                @RequestParam(required = false) String direction,
                                                                                @RequestParam(value = "from", defaultValue = "0")
                                                                          @PositiveOrZero Integer from,
                                                                                @RequestParam(value = "size", defaultValue = "3")
                                                                          @Positive Integer size) {
        log.info("GET: /event/program?date={}&program={}&direction={}&from={}&size={}", date, program, direction, from, size);
        return ResponseEntity.ok().body(eventService.getGrandFinalEvents(date, program, direction, from, size));
    }
}
