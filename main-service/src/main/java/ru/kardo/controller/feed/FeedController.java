package ru.kardo.controller.feed;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.kardo.dto.feed.CreateFeedDto;
import ru.kardo.dto.feed.FeedFullDto;
import ru.kardo.dto.feed.UpdateFeedDto;
import ru.kardo.model.User;
import ru.kardo.service.FeedService;
import ru.kardo.service.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/user/feed")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = {"http://localhost:3000", "https://kardo.zapto.org",
        "https://kardo-frontend.vercel.app", "http://51.250.32.102:8080"})
public class FeedController {

    private final FeedService service;
    private final UserService userService;

    @PostMapping("/post")
    public ResponseEntity<FeedFullDto> makePost(Principal principal, @RequestBody @Valid CreateFeedDto dto) {
        User user = userService.findUserByEmail(principal.getName());
        return ResponseEntity.ok(service.addNewPost(user.getId(), dto));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<FeedFullDto> updatePost(Principal principal, @PathVariable("id") @Positive Long feedId, @RequestBody @Valid UpdateFeedDto dto) {
        User user = userService.findUserByEmail(principal.getName());
        return ResponseEntity.ok(service.updatePost(user.getId(), feedId, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeed(Principal principal, @PathVariable("id") @Positive Long feedId) {
        User user = userService.findUserByEmail(principal.getName());
        service.deleteById(user.getId(), feedId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<FeedFullDto>> getFeed(Principal principal,
                                                     @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                                     @RequestParam(defaultValue = "5") @Positive Integer size) {
        User user = userService.findUserByEmail(principal.getName());
        return ResponseEntity.ok(service.getFeed(user.getId(), from, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeedFullDto> getFeedById(@PathVariable("id") @Positive Long feedId) {
        return ResponseEntity.ok(service.getById(feedId));
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<Void> addLike(Principal principal, @PathVariable("id") @Positive Long feedId) {
        User user = userService.findUserByEmail(principal.getName());
        service.addLike(user.getId(), feedId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
