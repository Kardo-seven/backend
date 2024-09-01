package ru.kardo.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kardo.dto.feed.FeedFullDto;
import ru.kardo.dto.profile.ProfilePreviewDtoResponse;

import java.util.LinkedHashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentFullDto {
    private Long id;

    private ProfilePreviewDtoResponse owner;

    private FeedFullDto feed;

    private String text;

    private Set<Long> likes;

    private Set<Long> replies = new LinkedHashSet<>();
}
