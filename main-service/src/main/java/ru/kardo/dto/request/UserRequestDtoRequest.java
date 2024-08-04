package ru.kardo.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kardo.model.enums.DirectionEnum;
import ru.kardo.model.enums.Gender;
import ru.kardo.model.enums.TypeOfSelection;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDtoRequest {

    @Pattern(regexp = "^[ а-яА-Яa-zA-Z]*$")
    private String name;

    @Pattern(regexp = "^[ а-яА-Яa-zA-Z]*$")
    private String lastName;

    @Pattern(regexp = "^[ а-яА-Яa-zA-Z]*$")
    private String surName;

    @Pattern(regexp = "^(\\+7( )?)?((\\(\\d{3}\\))|\\d{3})( )?\\d{3}[- ]?\\d{2}[- ]?\\d{2}$")
    private String phone;

    @Email
    private String email;

    @Past(message = "Birthdate cant be in future")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private List<String> linkList;

    @Enumerated(EnumType.STRING)
    private TypeOfSelection typeOfSelection;

    private List<DirectionEnum> directionEnumList;
}
