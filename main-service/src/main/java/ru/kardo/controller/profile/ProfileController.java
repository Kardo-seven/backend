package ru.kardo.controller.profile;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.kardo.dto.profile.*;
import ru.kardo.model.User;
import ru.kardo.service.ProfileService;
import ru.kardo.service.UserService;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/user/profile")
@RequiredArgsConstructor
@Slf4j
public class ProfileController {

    private final ProfileService profileService;
    private final UserService userService;

    @PatchMapping("/personal-information/update")
    public ResponseEntity<ProfileDtoResponse> personalInformationUpdate(Principal principal,
                                                                        @Valid @RequestBody ProfileUpdateDtoRequest profileUpdateDtoRequest) {
        User user = userService.findUserByEmail(principal.getName());
        log.info("PATCH: /user/profile/personal-information/update/{}", user.getEmail());
        return ResponseEntity.ok().body(profileService.personalInformationUpdate(user.getId(), profileUpdateDtoRequest));
    }

    @PostMapping("/social-network/add")
    public ResponseEntity<ProfileDtoResponse> addSocialNetworkLink(Principal principal,
                                                                   @RequestBody SocialNetworkDtoRequest socialNetworkDtoRequest) {
        User user = userService.findUserByEmail(principal.getName());
        log.info("POST: /user/profile/social-network/add/{}", user.getEmail());
        return ResponseEntity.status(201).body(profileService.addSocialNetworkLink(user.getId(), socialNetworkDtoRequest));
    }

    @PostMapping("/avatar/upload")
    public ResponseEntity<AvatarDtoResponse> uploadAvatar(Principal principal,
                                                          @RequestParam("file") @RequestPart MultipartFile multipartFile) throws IOException {
        User user = userService.findUserByEmail(principal.getName());
        log.info("POST: /user/profile/avatar/upload/{}", user.getEmail());
        return ResponseEntity.status(201).body(profileService.uploadAvatar(user.getId(), multipartFile));
    }

    @GetMapping("/avatar")
    public ResponseEntity<AvatarDtoResponse> getAvatar(Principal principal) {
        User user = userService.findUserByEmail(principal.getName());
        log.info("GET: /user/profile/avatar/{}", user.getEmail());
        return ResponseEntity.ok().body(profileService.getAvatar(user.getId()));
    }

    @GetMapping()
    public ResponseEntity<ProfileDtoResponse> getProfile(Principal principal) {
        User user = userService.findUserByEmail(principal.getName());
        log.info("GET: /user/profile/{}", user.getEmail());
        return ResponseEntity.ok().body(profileService.getProfile(user.getId()));
    }

    @PostMapping("/publication/upload")
    public ResponseEntity<PublicationDtoResponse> uploadPublication(Principal principal,
                                                                    @RequestParam("file") @RequestPart MultipartFile multipartFile,
                                                                    @RequestParam (value = "description", required = false) String description) throws IOException {
        User user = userService.findUserByEmail(principal.getName());
        log.info("POST: /user/profile/publication/upload/{}", user.getEmail());
        return ResponseEntity.status(201).body(profileService.uploadPublication(user.getId(), multipartFile, description));
    }

    @GetMapping("/publications")
    public ResponseEntity<List<PublicationDtoResponse>> getPublications(Principal principal) {
        User user = userService.findUserByEmail(principal.getName());
        log.info("GET: /user/profile/publications/{}", user.getEmail());
        return ResponseEntity.ok().body(profileService.getPublications(user.getId()));
    }

    @GetMapping("/publication/{publicationId}")
    public ResponseEntity<PublicationDtoResponse> getPublication(Principal principal, @PathVariable Long publicationId) {
        User user = userService.findUserByEmail(principal.getName());
        log.info("GET: /user/profile/publication/{}", publicationId);
        return ResponseEntity.ok().body(profileService.getPublication(publicationId, user.getId()));
    }
}
