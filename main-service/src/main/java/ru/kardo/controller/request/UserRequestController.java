package ru.kardo.controller.request;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kardo.dto.request.UserRequestDtoRequest;
import ru.kardo.dto.request.UserRequestDtoResponse;
import ru.kardo.model.User;
import ru.kardo.service.UserRequestService;
import ru.kardo.service.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/request")
@RequiredArgsConstructor
@Slf4j
public class UserRequestController {

    private final UserService userService;
    private final UserRequestService userRequestService;

    @PostMapping("/{eventId}")
    public ResponseEntity<UserRequestDtoResponse> postUserRequest(Principal principal, @PathVariable Long eventId,
                                                                  @Valid @RequestBody UserRequestDtoRequest userRequestDtoRequest) {
        User user = userService.findUserByEmail(principal.getName());
        log.info("POST: /request/{} for {}", eventId, user.getEmail());
        return ResponseEntity.status(201).body(userRequestService.postUserRequest(user.getId(), eventId, userRequestDtoRequest));
    }
}
