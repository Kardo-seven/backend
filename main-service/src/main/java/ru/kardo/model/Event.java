package ru.kardo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kardo.model.enums.ProgramEnum;

import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "description")
    private String description;

    @Column(name = "program")
    @Enumerated(EnumType.STRING)
    private ProgramEnum programEnum;

    @Column(name = "country")
    private String country;

    @Column(name = "location")
    private String location;
}
