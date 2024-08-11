package ru.kardo.mapper;

import org.mapstruct.Mapper;
import ru.kardo.dto.feed.FeedFullDto;
import ru.kardo.model.Feed;

@Mapper(componentModel = "spring", uses = {CommentMapper.class})
public interface FeedMapper {
    FeedFullDto toFeedFullDto(Feed feed);
}
