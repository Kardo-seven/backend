package ru.kardo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kardo.dto.comment.CreateCommentDto;
import ru.kardo.repo.CommentRepo;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl {

    private final CommentRepo commentRepo;

    public void addNewComment(CreateCommentDto dto) {

    }
}
