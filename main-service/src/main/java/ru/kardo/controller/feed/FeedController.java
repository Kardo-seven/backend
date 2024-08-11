package ru.kardo.controller.feed;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kardo.dto.feed.CreateFeedDto;
import ru.kardo.dto.feed.UpdateFeedDto;
import ru.kardo.model.User;
import ru.kardo.service.ActivityFeedServiceImpl;
import ru.kardo.service.UserService;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/user/feed")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = {"http://localhost:3000", "https://kardo.zapto.org",
        "https://kardo-frontend.vercel.app", "http://51.250.32.102:8080"})
public class FeedController {

    private final ActivityFeedServiceImpl service;
    private final UserService userService;

    @PostMapping("/post")
    public void makePost(Principal principal, @RequestBody @Valid CreateFeedDto dto) {
        User user = userService.findUserByEmail(principal.getName());
        service.addNewPost(user.getId(), dto);
    }

    @PatchMapping("/update/{id}")
    public void updatePost(Principal principal, @PathVariable @Positive Long id, @RequestBody @Valid UpdateFeedDto dto) {
        User user = userService.findUserByEmail(principal.getName());
        service.updatePost(user.getId(), id, dto);
    }

    @GetMapping
    public void getFeed() {
        //лента выгружается
    }

    @GetMapping("/{id}")
    public void getFeedById(@PathVariable @Positive Long id) {

    }

    @DeleteMapping("/{id}")
    public void deleteFeed(@PathVariable @Positive Long id) {

    }
}
