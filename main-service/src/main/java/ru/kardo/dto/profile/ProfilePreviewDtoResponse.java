package ru.kardo.dto.profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfilePreviewDtoResponse {

    private Long id;

    private String name;

    private String email;

    private String avatarLink;
}
