package ru.kardo.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kardo.model.Direction;
import ru.kardo.model.EventImage;
import ru.kardo.model.enums.EventType;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDtoResponse {

    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    private String description;

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    private String location;

    private String title;

    private Boolean isGrandFinalEvent;

    private EventImage eventImage;

    private Set<Direction> directionSet;
}
