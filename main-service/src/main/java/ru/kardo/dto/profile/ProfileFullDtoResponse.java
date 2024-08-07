package ru.kardo.dto.profile;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kardo.model.Link;
import ru.kardo.model.enums.Gender;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileFullDtoResponse {

    private Long id;

    private String name;

    private String lastName;

    private String surName;

    private String phone;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String country;

    private String region;

    private String city;

    private String citizenship;

    private String avatarLink;

    private Long userId;

    private Set<Link> linkSet;

    private List<PublicationDtoResponse> publicationDtoResponseList;

    private Boolean isChild;

    private Boolean isChildExpert;
}
