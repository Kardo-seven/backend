package ru.kardo.dto.feed;

import ru.kardo.dto.comment.CommentDto;
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
