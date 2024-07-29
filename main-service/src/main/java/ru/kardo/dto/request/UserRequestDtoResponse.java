package ru.kardo.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kardo.model.Event;
import ru.kardo.model.Link;
import ru.kardo.model.Profile;
import ru.kardo.model.RequestPreview;
import ru.kardo.model.enums.Gender;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDtoResponse {

    private Long id;

    private String name;

    private String lastName;

    private String surName;

    private String phone;

    private String email;

    private LocalDate birthday;

    private Gender gender;

    private Long requestPreviewId;

    private Long profileId;

    private Long eventId;

    private LocalDateTime created;

    private Set<Link> linkSet;
}
