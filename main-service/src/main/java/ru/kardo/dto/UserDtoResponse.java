package ru.kardo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kardo.model.Authority;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoResponse {

    private Long id;

    private String email;

    private Set<Authority> authoritySet;
}
