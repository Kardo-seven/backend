package ru.kardo.controller.feed;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kardo.dto.comment.CommentFullDto;
import ru.kardo.dto.comment.CreateCommentDto;
import ru.kardo.dto.comment.UpdateCommentDto;
import ru.kardo.model.User;
import ru.kardo.service.CommentService;
import ru.kardo.service.UserService;

import java.security.Principal;
import java.util.Set;

@RestController
@RequestMapping("/user/feed/{feed_id}/comments")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = {"http://localhost:3000", "https://kardo.zapto.org",
        "https://kardo-frontend.vercel.app", "http://51.250.32.102:8080"})
public class CommentController {

    private final CommentService service;
    private final UserService userService;

    @PostMapping("/post")
    public ResponseEntity<CommentFullDto> postComment(Principal principal,
                                                      @PathVariable("feed_id") Long feedId, @RequestBody CreateCommentDto dto) {
        User user = userService.findUserByEmail(principal.getName());
        return ResponseEntity.ok(service.addNewComment(user.getId(), feedId, dto));
    }

    @PatchMapping("/{comm_id}")
    public ResponseEntity<CommentFullDto> update(Principal principal, @PathVariable("feed_id") Long feedId,
                                                 @PathVariable("comm_id") Long commentId, @RequestBody UpdateCommentDto dto) {
        User user = userService.findUserByEmail(principal.getName());
        return ResponseEntity.ok(service.updateComment(user.getId(), feedId, commentId, dto));
    }

    @DeleteMapping("/{comm_id}")
    public ResponseEntity<Void> delete(Principal principal,
                                       @PathVariable("feed_id") Long feedId, @PathVariable("comm_id") Long commentId) {
        User user = userService.findUserByEmail(principal.getName());
        service.deleteComment(user.getId(), feedId, commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{comm_id}/reply")
    public ResponseEntity<CommentFullDto> reply(Principal principal, @PathVariable("feed_id") Long feedId,
                                                @PathVariable("comm_id") Long commentId, @RequestBody CreateCommentDto dto) {
        User user = userService.findUserByEmail(principal.getName());
        return ResponseEntity.ok(service.replyComment(user.getId(), feedId, commentId, dto));
    }

    @PostMapping("/{comm_id}")
    public ResponseEntity<Void> like(Principal principal,
                                     @PathVariable("feed_id") Long feedId, @PathVariable("comm_id") Long commentId) {
        User user = userService.findUserByEmail(principal.getName());
        service.likeFeedComment(user.getId(), feedId, commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Set<CommentFullDto>> getFeedComments(@PathVariable("feed_id") Long feedId) {
        return ResponseEntity.ok(service.getFeedComments(feedId));
    }

    @GetMapping("/{comm_id}/replies")
    public ResponseEntity<Set<CommentFullDto>> getFeedCommentReplies(@PathVariable("feed_id") Long feedId, @PathVariable("comm_id") Long commentId) {
        return ResponseEntity.ok(service.getFeedCommentReplies(feedId, commentId));
    }

    @GetMapping("/{comm_id}")
    public ResponseEntity<CommentFullDto> getComment(@PathVariable("feed_id") Long feedId, @PathVariable("comm_id") Long commentId) {
        return ResponseEntity.ok(service.getComment(feedId, commentId));
    }
}
