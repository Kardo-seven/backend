package ru.kardo.controller.feed;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kardo.dto.comment.CreateCommentDto;
import ru.kardo.service.CommentServiceImpl;

@RestController
@RequestMapping("/user/feed/comment")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = {"http://localhost:3000", "https://kardo.zapto.org",
        "https://kardo-frontend.vercel.app", "http://51.250.32.102:8080"})
public class CommentController {

    private final CommentServiceImpl service;

    @PostMapping("/post")
    public void postComment(@RequestBody CreateCommentDto dto) {
service.addNewComment(dto);
    }
}
