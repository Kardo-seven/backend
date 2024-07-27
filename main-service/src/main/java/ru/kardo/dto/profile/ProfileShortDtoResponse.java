package ru.kardo.dto.profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileShortDtoResponse {

    private Long id;

    private String name;

    private String email;
}
