package ru.kardo.dto.event;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventImageDtoResponse {

    private Long id;

    private String title;

    private String type;

    private String link;
}
