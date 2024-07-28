package ru.kardo.controller.profile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kardo.dto.profile.*;
import ru.kardo.service.ProfileService;
import java.util.List;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
@Slf4j
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/{profileId}/avatar")
    public ResponseEntity<AvatarDtoResponse> getAvatar(@PathVariable Long profileId) {
        log.info("GET: /profile/{}/avatar", profileId);
        return ResponseEntity.ok().body(profileService.getAvatar(profileId));
    }

    @GetMapping("/{profileId}")
    public ResponseEntity<ProfileShortDtoResponse> getProfile(@PathVariable Long profileId) {
        log.info("GET: /profile/{}", profileId);
        return ResponseEntity.ok().body(profileService.getProfile(profileId));
    }

    @GetMapping("/{profileId}/publications")
    public ResponseEntity<List<PublicationDtoResponse>> getPublications(@PathVariable Long profileId) {
        log.info("GET: /profile/{}/publications", profileId);
        return ResponseEntity.ok().body(profileService.getPublications(profileId));
    }

    @GetMapping("/{profileId}/publication/{publicationId}")
    public ResponseEntity<PublicationDtoResponse> getPublication(@PathVariable Long profileId, @PathVariable Long publicationId) {
        log.info("GET: /profile/{}/publication/{}", profileId, publicationId);
        return ResponseEntity.ok().body(profileService.getPublication(publicationId, profileId));
    }

    @GetMapping("/{profileId}/subscribers")
    public ResponseEntity<List<ProfilePreviewDtoResponse>> getSubscribers(@PathVariable Long profileId) {
        log.info("GET: /profile/{}/subscribers", profileId);
        return ResponseEntity.ok().body(profileService.getSubscribers(profileId));
    }

    @GetMapping("/{profileId}/subscriptions")
    public ResponseEntity<List<ProfilePreviewDtoResponse>> getSubscriptions(@PathVariable Long profileId) {
        log.info("GET: /profile/{}/subscriptions", profileId);
        return ResponseEntity.ok().body(profileService.getSubscriptions(profileId));
    }
}
