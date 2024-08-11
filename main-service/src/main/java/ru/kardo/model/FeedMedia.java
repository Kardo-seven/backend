package ru.kardo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "feed_media")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FeedMedia {
    @Id
    private Long id;

    private String title;

    private String type;

    private String link;
}
