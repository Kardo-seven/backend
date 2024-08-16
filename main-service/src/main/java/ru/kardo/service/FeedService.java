package ru.kardo.service;

import ru.kardo.dto.feed.CreateFeedDto;
import ru.kardo.dto.feed.FeedFullDto;
import ru.kardo.dto.feed.UpdateFeedDto;

import java.util.List;

public interface FeedService {
    FeedFullDto addNewPost(Long userId, CreateFeedDto dto);

    FeedFullDto updatePost(Long userId, Long feedId, UpdateFeedDto dto);

    List<FeedFullDto> getFeed(long id, Integer from, Integer size);

    FeedFullDto getById(Long id);

    void deleteById(Long ownerId, Long id);

    void addLike(Long userId, Long id);
}
