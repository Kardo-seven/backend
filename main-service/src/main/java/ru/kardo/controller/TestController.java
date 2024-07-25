package ru.kardo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kardo.model.User;
import ru.kardo.service.UserService;

import java.security.Principal;

@RestController
@RequestMapping("test")
@RequiredArgsConstructor
@Slf4j
public class TestController {

    private final UserService userService;

    @GetMapping()
    @PreAuthorize("hasAuthority('SCOPE_EXPERT')")
    ResponseEntity<String> getString(Principal principal) {
        User user = userService.findUserByEmail(principal.getName());
        return ResponseEntity.ok().body(user.getEmail());
    }
}
