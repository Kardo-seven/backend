package ru.kardo.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kardo.model.Direction;
import ru.kardo.model.EventImage;
import ru.kardo.model.enums.EventType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDtoResponse {

    private Long id;

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    private String title;

    private EventImageDtoResponse eventImageDtoResponse;

//    private Set<Direction> directionSet;
}
