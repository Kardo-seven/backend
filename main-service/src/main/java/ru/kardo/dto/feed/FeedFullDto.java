package ru.kardo.dto.feed;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kardo.dto.profile.ProfilePreviewDtoResponse;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FeedFullDto {
    private Long id;

    private ProfilePreviewDtoResponse owner;

    private Set<FeedMediaDto> media;

    private String description;

    private LocalDateTime created;

    private Set<Long> comments = new LinkedHashSet<>();

    private Set<Long> likes;
}
