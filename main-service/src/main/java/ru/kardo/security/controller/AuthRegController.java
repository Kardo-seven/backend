package ru.kardo.security.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.kardo.dto.user.AuthDtoRequest;
import ru.kardo.dto.user.UserDtoRequest;
import ru.kardo.dto.user.UserDtoResponse;
import ru.kardo.security.service.TokenService;
import ru.kardo.service.UserService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthRegController {

    private final UserService userService;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/registration")
    public ResponseEntity<UserDtoResponse> registrationUser(@Valid @RequestBody UserDtoRequest userDtoRequest) {
        log.info("POST: /registration");
        return ResponseEntity.status(201).body(userService.addUser(userDtoRequest));
    }

    @PostMapping("/authorization")
    public ResponseEntity<String> getToken(@Valid @RequestBody AuthDtoRequest authDtoRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authDtoRequest.getEmail(), authDtoRequest.getPassword()));
        log.info("POST: /authorization");
        return ResponseEntity.ok().body(tokenService.generateToken(authentication));
    }
}
