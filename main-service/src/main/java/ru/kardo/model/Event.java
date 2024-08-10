package ru.kardo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kardo.model.enums.EventType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

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

//    @Column(name = "event_date")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
//    private LocalDate eventDate;
//
//    @Column(name = "start_time")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime startTime;
//
//    @Column(name = "end_time")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime endTime;
//
//    @Column(name = "description")
//    private String description;

    @Column(name = "event_type")
    @Enumerated(EnumType.STRING)
    private EventType eventType;

//    @Column(name = "location")
//    private String location;

    @Column(name = "title")
    private String title;

//    @Column(name = "is_grand_final_event")
//    private Boolean isGrandFinalEvent;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", unique = true)
    private EventImage eventImage;

//    @ElementCollection(fetch = FetchType.LAZY)
//    @CollectionTable(name = "event_directions" , joinColumns = @JoinColumn(name = "event_id"))
//    @Column(name = "direction")
//    @AttributeOverrides({
//            @AttributeOverride(name = "direction", column = @Column(name = "direction"))
//    })
//    private Set<Direction> directionSet;
}
