package ru.kardo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "feed")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Feed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feed_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Profile owner;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "feed_media_id")
    private Set<FeedMedia> media;

    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "feed_likes", joinColumns = @JoinColumn(name = "feed_id"))
    @Column(name = "user_id")
    private Set<Long> likes = new HashSet<>();
}
