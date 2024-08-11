package ru.kardo.dto.feed;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import ru.kardo.dto.CommentDto;
import ru.kardo.model.Comment;
import ru.kardo.model.FeedMedia;
import ru.kardo.model.Profile;

import java.time.LocalDateTime;
import java.util.Set;

public class FeedFullDto {
    private Long id;

    private Profile owner;

    private FeedMedia media;

    private String description;

    private LocalDateTime created;

    private Set<CommentDto> comments;

    private Set<Long> likes;
}
