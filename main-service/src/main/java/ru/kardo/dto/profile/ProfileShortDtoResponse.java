package ru.kardo.dto.profile;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kardo.model.enums.Gender;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileShortDtoResponse {

    private Long id;

    private String name;

    private String lastName;

    private String surName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String avatarLink;

    private List<PublicationDtoResponse> publicationDtoResponseList;
}
