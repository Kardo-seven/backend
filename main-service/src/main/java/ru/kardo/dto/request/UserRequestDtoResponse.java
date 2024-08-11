package ru.kardo.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kardo.model.*;
import ru.kardo.model.enums.DirectionEnum;
import ru.kardo.model.enums.Gender;
import ru.kardo.model.enums.RequestStatus;
import ru.kardo.model.enums.TypeOfSelection;

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

    private String address;

    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private TypeOfSelection typeOfSelection;

    private Long requestPreviewId;

    private Long profileId;

    private Long eventId;

    private Long grandFinalEventId;

    @Enumerated(EnumType.STRING)
    private RequestStatus requestStatus;

    private LocalDateTime created;

    private Set<Link> linkSet;

    private Set<Direction> directionSet;
}
