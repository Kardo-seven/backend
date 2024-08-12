package ru.kardo.dto.event;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kardo.model.enums.EventType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDtoResponse {

    private Long id;

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    private String title;

    private EventImageDtoResponse eventImageDtoResponse;

}
