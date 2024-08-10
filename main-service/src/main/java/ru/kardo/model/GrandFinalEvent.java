package ru.kardo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kardo.model.enums.EventType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "grand_final_events")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrandFinalEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grand_final_event_id")
    Long id;

    @Column(name = "grand_final_event_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate eventDate;

    @Column(name = "start_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    @Column(name = "description")
    private String description;

    @Column(name = "event_type")
    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @Column(name = "location")
    private String location;

    @Column(name = "title")
    private String title;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "grand_final_event_directions" , joinColumns = @JoinColumn(name = "grand_final_event_id"))
    @Column(name = "direction")
    @AttributeOverrides({
            @AttributeOverride(name = "direction", column = @Column(name = "direction"))
    })
    private Set<Direction> directionSet;
}
