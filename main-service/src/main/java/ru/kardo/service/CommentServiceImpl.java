package ru.kardo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kardo.dto.comment.CommentFullDto;
import ru.kardo.dto.comment.CreateCommentDto;
import ru.kardo.dto.comment.UpdateCommentDto;
import ru.kardo.exception.ConflictException;
import ru.kardo.exception.NotFoundValidationException;
import ru.kardo.mapper.CommentMapper;
import ru.kardo.model.Comment;
import ru.kardo.model.Feed;
import ru.kardo.model.Profile;
import ru.kardo.repo.CommentRepo;
import ru.kardo.repo.FeedRepo;
import ru.kardo.repo.ProfileRepo;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final FeedRepo feedRepo;
    private final ProfileRepo profileRepo;
    private final CommentRepo commentRepo;

    private final CommentMapper commentMapper;

    public CommentFullDto addNewComment(Long userId, Long feedId, CreateCommentDto dto) {
        Profile owner = checkIfProfileExists(userId);
        Feed feed = checkIfFeedExists(feedId);
        Comment comment = commentMapper.toComment(dto);
        comment.setFeed(feed);
        comment.setOwner(owner);
        comment.setCreated(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        comment = commentRepo.saveAndFlush(comment);
        return commentMapper.toCommentFullDto(comment);
    }

    public CommentFullDto updateComment(Long userId, Long feedId, Long commentId, UpdateCommentDto dto) {
        checkIfProfileExists(userId);
        Feed feed = checkIfFeedExists(feedId);
        Comment comment = checkIfCommentExists(dto.getId());
        Set<Comment> comments = commentRepo.findAllByFeedIdOrderByIdDesc(feed.getId());
        validateComment(comments, dto.getId());
        validateOwner(userId, comment.getOwner().getId());
        if (dto.getText() != null) {
            comment.setText(dto.getText());
        }
        if (dto.getId() != null) {
            comment.setId(dto.getId());
        }
        comment = commentRepo.saveAndFlush(comment);
        return commentMapper.toCommentFullDto(comment);
    }

    public void deleteComment(Long userId, Long feedId, Long commentId) {
        Feed feed = checkIfFeedExists(feedId);
        Comment comment = checkIfCommentExists(commentId);
        checkIfProfileExists(userId);
        validateOwner(userId, comment.getOwner().getId());
        Set<Comment> comments = commentRepo.findAllByFeedIdOrderByIdDesc(feed.getId());
        validateComment(comments, commentId);
        commentRepo.deleteById(commentId);
    }

    public CommentFullDto replyComment(Long userId, Long feedId, Long commentId, CreateCommentDto dto) {
        Profile owner = checkIfProfileExists(userId);
        checkIfFeedExists(feedId);
        Comment comment = checkIfCommentExists(commentId);
        Comment reply = new Comment();
        reply.setOwner(owner);
        reply.setText(dto.getText());
        reply = commentRepo.saveAndFlush(reply);
        comment.getReplies().add(reply.getId());
        commentRepo.saveAndFlush(comment);
        return commentMapper.toCommentFullDto(reply);
    }

    public void likeFeedComment(Long userId, Long feedId, Long commentId) {
        Feed feed = checkIfFeedExists(feedId);
        checkIfProfileExists(userId);
        Set<Comment> comments = commentRepo.findAllByFeedIdOrderByIdDesc(feed.getId());
        Comment comm = checkIfCommentExists(commentId);
        validateComment(comments, commentId);
        if (comm.getLikes().contains(commentId)) {
            comm.getLikes().remove(userId);
        } else {
            comm.getLikes().add(userId);
        }
        commentRepo.saveAndFlush(comm);
    }

    public Set<CommentFullDto> getFeedComments(Long feedId) {
        Feed feed = checkIfFeedExists(feedId);
        Set<Comment> comments = commentRepo.findAllByFeedIdOrderByIdDesc(feed.getId());
        return comments.stream().map(commentMapper::toCommentFullDto).collect(Collectors.toUnmodifiableSet());
    }

    public Set<CommentFullDto> getFeedCommentReplies(Long feedId, Long commentId) {
        Feed feed = checkIfFeedExists(feedId);
        Comment comment = checkIfCommentExists(commentId);
        Set<Comment> comments = commentRepo.findAllByFeedIdOrderByIdDesc(feed.getId());
        validateComment(comments, commentId);
        return comment.getReplies().stream()
                .map(this::checkIfCommentExists)
                .map(commentMapper::toCommentFullDto)
                .collect(Collectors.toUnmodifiableSet());
    }

    public CommentFullDto getComment(Long feedId, Long commentId) {
        Feed feed = checkIfFeedExists(feedId);
        Comment comm = checkIfCommentExists(commentId);
        Set<Comment> comments = commentRepo.findAllByFeedIdOrderByIdDesc(feed.getId());
        validateComment(comments, commentId);
        return commentMapper.toCommentFullDto(comm);
    }

    private void validateOwner(Long userId, Long ownerId) {
        if (!ownerId.equals(userId)) {
            throw new ConflictException("User with id " + userId + " cannot update/delete this comment");
        }
    }

    private void validateComment(Set<Comment> comments, Long commentId) {
        if (comments.stream()
                .noneMatch(com -> com.getId().equals(commentId) //поиск айди в комментарии
                        || !com.getReplies().isEmpty() && com.getReplies().contains(commentId))) { //поиск айди в ответах на этот комментарий
            throw new NotFoundValidationException("Comment with id " + commentId + " not found for this feed");
        }
    }

    private Profile checkIfProfileExists(Long userId) {
        return profileRepo.findById(userId).orElseThrow(() ->
                new NotFoundValidationException("Profile for user with id " + userId + " not found"));
    }

    private Feed checkIfFeedExists(Long feedId) {
        return feedRepo.findById(feedId).orElseThrow(() ->
                new NotFoundValidationException("Feed with id " + feedId + " not found"));
    }

    private Comment checkIfCommentExists(Long id) {
        return commentRepo.findById(id).orElseThrow(() ->
                new NotFoundValidationException("Comment with id " + id + " not found"));
    }
}
