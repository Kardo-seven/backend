package ru.kardo.controller.request;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.kardo.dto.request.RequestPreviewDtoResponse;
import ru.kardo.dto.request.UserRequestDtoRequest;
import ru.kardo.dto.request.UserRequestDtoResponse;
import ru.kardo.model.User;
import ru.kardo.service.UserRequestService;
import ru.kardo.service.UserService;
import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("user/request")
@RequiredArgsConstructor
@Slf4j
public class UserRequestController {

    private final UserService userService;
    private final UserRequestService userRequestService;

    @PostMapping("/{eventId}")
    public ResponseEntity<UserRequestDtoResponse> postUserRequest(Principal principal, @PathVariable Long eventId,
                                                                  @Valid @RequestBody UserRequestDtoRequest userRequestDtoRequest) {
        User user = userService.findUserByEmail(principal.getName());
        log.info("POST: /user/request/{} for {}", eventId, user.getEmail());
        return ResponseEntity.status(201).body(userRequestService.postUserRequest(user.getId(), eventId, userRequestDtoRequest));
    }

    @PostMapping("/{eventId}/upload-preview")
    public ResponseEntity<RequestPreviewDtoResponse> uploadPreview(Principal principal, @PathVariable Long eventId,
                                                                   @RequestParam("file") @RequestPart MultipartFile multipartFile) throws IOException {
        User user = userService.findUserByEmail(principal.getName());
        log.info("POST: /user/request/{}/upload-preview for {}", eventId, user.getEmail());
        return ResponseEntity.status(201).body(userRequestService.uploadRequestPreview(user.getId(), eventId, multipartFile));
    }

    @PatchMapping("/{requestId}")
    public ResponseEntity<UserRequestDtoResponse> patchUserRequest(Principal principal, @PathVariable Long requestId,
                                                                   @Valid @RequestBody UserRequestDtoRequest userRequestDtoRequest) {
        User user = userService.findUserByEmail(principal.getName());
        log.info("PATCH: /user/request/{}", requestId);
        return ResponseEntity.ok().body(userRequestService.patchUserRequest(user.getId(), requestId, userRequestDtoRequest));
    }
}
