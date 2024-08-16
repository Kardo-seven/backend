package ru.kardo.service;

import ru.kardo.dto.comment.CommentFullDto;
import ru.kardo.dto.comment.CreateCommentDto;
import ru.kardo.dto.comment.UpdateCommentDto;

import java.util.Set;

public interface CommentService {
    CommentFullDto addNewComment(Long userId, Long feedId, CreateCommentDto dto);

    CommentFullDto updateComment(Long userId, Long feedId, Long commentId, UpdateCommentDto dto);

    void deleteComment(Long userId, Long feedId, Long commentId);

    CommentFullDto replyComment(Long userId, Long feedId, Long commentId, CreateCommentDto dto);

    void likeFeedComment(Long userId, Long feedId, Long commentId);

    Set<CommentFullDto> getFeedComments(Long feedId);

    Set<CommentFullDto> getFeedCommentReplies(Long feedId, Long commentId);

    CommentFullDto getComment(Long feedId, Long commentId);
}
