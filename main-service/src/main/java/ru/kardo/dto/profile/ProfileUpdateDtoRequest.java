package ru.kardo.dto.profile;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kardo.model.enums.DirectionEnum;
import ru.kardo.model.enums.Gender;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileUpdateDtoRequest {

    @Pattern(regexp = "^[ а-яА-Яa-zA-Z]*$")
    private String name;

    @Pattern(regexp = "^[ а-яА-Яa-zA-Z]*$")
    private String lastName;

    @Pattern(regexp = "^[ а-яА-Яa-zA-Z]*$")
    private String surName;

    @Pattern(regexp = "^(\\+7( )?)?((\\(\\d{3}\\))|\\d{3})( )?\\d{3}[- ]?\\d{2}[- ]?\\d{2}$")
    private String phone;

    @Past(message = "Birthdate cant be in future")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String country;

    private String region;

    private String city;

    private String citizenship;

    private List<String> linkList;

    private Boolean isChild;

    private Boolean isChildExpert;

    private Set<String> seasons;

    private Set<DirectionEnum> directions;
}
