package ru.kardo.controller.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.kardo.dto.AvatarDtoResponse;
import ru.kardo.dto.ProfileUpdateDtoRequest;
import ru.kardo.dto.ProfileUpdateDtoResponse;
import ru.kardo.model.User;
import ru.kardo.service.ProfileService;
import ru.kardo.service.UserService;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final ProfileService profileService;
    private final UserService userService;

    @PatchMapping("/personal-information/update")
    public ResponseEntity<ProfileUpdateDtoResponse> personalInformationUpdate(Principal principal,
                                                                  @Valid @RequestBody ProfileUpdateDtoRequest profileUpdateDtoRequest) {
        User user = userService.findUserByEmail(principal.getName());
        log.info("PATH /user/personal-information/update/{}", user.getEmail());
        return ResponseEntity.ok().body(profileService.personalInformationUpdate(user.getId(), profileUpdateDtoRequest));
    }

    @PostMapping("/avatar/upload")
    public ResponseEntity<AvatarDtoResponse> uploadAvatar(Principal principal,
                                                          @RequestParam("file") @RequestPart MultipartFile multipartFile) throws IOException {
        User user = userService.findUserByEmail(principal.getName());
        log.info("POST: /user/avatar/upload/{}", user.getEmail());
        return ResponseEntity.status(201).body(profileService.uploadAvatar(user.getId(), multipartFile));
    }

    @GetMapping("/avatar/get")
    public ResponseEntity<AvatarDtoResponse> getAvatar(Principal principal) {
        User user = userService.findUserByEmail(principal.getName());
        log.info("GET: /user/avatar/get/{}", user.getEmail());
        return ResponseEntity.ok().body(profileService.getAvatar(user.getId()));
    }
}
