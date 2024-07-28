package ru.kardo.controller.profile;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.kardo.dto.profile.*;
import ru.kardo.model.User;
import ru.kardo.service.ProfileService;
import ru.kardo.service.UserService;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/user")
@Slf4j
@RequiredArgsConstructor
@CrossOrigin
public class UserController {

    private final ProfileService profileService;
    private final UserService userService;

    @PatchMapping("/personal-information/update")
    public ResponseEntity<ProfileFullDtoResponse> personalInformationUpdate(Principal principal,
                                                                            @Valid @RequestBody ProfileUpdateDtoRequest profileUpdateDtoRequest) {
        User user = userService.findUserByEmail(principal.getName());
        log.info("PATCH: /user/personal-information/update/{}", user.getEmail());
        return ResponseEntity.ok().body(profileService.personalInformationUpdate(user.getId(), profileUpdateDtoRequest));
    }

    @PostMapping("/social-network/add")
    public ResponseEntity<ProfileFullDtoResponse> addSocialNetworkLink(Principal principal,
                                                                       @RequestBody SocialNetworkDtoRequest socialNetworkDtoRequest) {
        User user = userService.findUserByEmail(principal.getName());
        log.info("POST: /user/social-network/add/{}", user.getEmail());
        return ResponseEntity.status(201).body(profileService.addSocialNetworkLink(user.getId(), socialNetworkDtoRequest));
    }

    @PostMapping("/avatar/upload")
    public ResponseEntity<AvatarDtoResponse> uploadAvatar(Principal principal,
                                                          @RequestParam("file") @RequestPart MultipartFile multipartFile) throws IOException {
        User user = userService.findUserByEmail(principal.getName());
        log.info("POST: /user/avatar/upload/{}", user.getEmail());
        return ResponseEntity.status(201).body(profileService.uploadAvatar(user.getId(), multipartFile));
    }

    @PostMapping("/publication/upload")
    public ResponseEntity<PublicationDtoResponse> uploadPublication(Principal principal,
                                                                    @RequestParam("file") @RequestPart MultipartFile multipartFile,
                                                                    @RequestParam (value = "description", required = false) String description) throws IOException {
        User user = userService.findUserByEmail(principal.getName());
        log.info("POST: /user/profile/publication/upload/{}", user.getEmail());
        return ResponseEntity.status(201).body(profileService.uploadPublication(user.getId(), multipartFile, description));
    }

    @PostMapping("/subscribe/{profileId}")
    public ResponseEntity<Void> subscribe(Principal principal, @PathVariable Long profileId) {
        User user = userService.findUserByEmail(principal.getName());
        log.info("POST: /user/profile/subscribe/{}", profileId);
        profileService.subscribe(user.getId(), profileId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/profile")
    public ResponseEntity<ProfileFullDtoResponse> getOwnProfile(Principal principal) {
        User user = userService.findUserByEmail(principal.getName());
        log.info("GET: /user/profile/{}", user.getEmail());
        return ResponseEntity.ok().body(profileService.getOwnProfile(user.getId()));
    }
}
