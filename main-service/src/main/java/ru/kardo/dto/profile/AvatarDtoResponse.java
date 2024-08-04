package ru.kardo.dto.profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvatarDtoResponse {

    private Long id;

    private String title;

    private String type;

    private String link;
}
