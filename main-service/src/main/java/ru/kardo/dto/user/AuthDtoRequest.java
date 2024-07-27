package ru.kardo.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthDtoRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
