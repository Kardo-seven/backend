package ru.kardo.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "feed")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Feed {
    @Id
    private Long id;

    @ManyToOne
    private Profile owner;

    @OneToMany
    private Set<FeedMedia> media;

    private String description;

    private LocalDateTime created;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "feed_comments", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "comment")
    private Set<Comment> comments;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "feed_likes", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "like")
    private Set<Long> likes;
}
