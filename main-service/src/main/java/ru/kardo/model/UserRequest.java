package ru.kardo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kardo.model.enums.Gender;
import ru.kardo.model.enums.RequestStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;

@Entity
@Table(name = "user_requests")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "sur_name")
    private String surName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "birthday")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToOne(mappedBy = "userRequest", fetch = FetchType.LAZY)
    private RequestPreview requestPreview;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Profile profile;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    @Column(name = "created")
    @Builder.Default
    private LocalDateTime created = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private RequestStatus requestStatus = RequestStatus.ACCEPTED;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "request_social_network_links", joinColumns = @JoinColumn(name = "request_id"))
    @Column(name = "link")
    @AttributeOverrides({
            @AttributeOverride(name = "link", column = @Column(name = "link"))
    })
    private Set<Link> linkSet;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "user_request_directions" , joinColumns = @JoinColumn(name = "request_id"))
    @Column(name = "direction")
    @AttributeOverrides({
            @AttributeOverride(name = "direction", column = @Column(name = "direction"))
    })
    private Set<Direction> directionSet;
}
