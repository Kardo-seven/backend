package ru.kardo.mapper;

import org.mapstruct.Mapper;
import ru.kardo.dto.comment.CommentFullDto;
import ru.kardo.dto.comment.CreateCommentDto;
import ru.kardo.model.Comment;

@Mapper(componentModel = "spring", uses = {ProfileMapper.class, FeedMapper.class})
public interface CommentMapper {
    Comment toComment(CreateCommentDto dto);

    CommentFullDto toCommentFullDto(Comment comment);
}
