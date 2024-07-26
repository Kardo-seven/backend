package ru.kardo.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kardo.model.Profile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvatarDtoResponse {

    private Long id;

    private String title;

    private String type;

    private String link;

    private Long profileId;
}
