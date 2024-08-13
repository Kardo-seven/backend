package ru.kardo.controller.profile;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kardo.dto.profile.*;
import ru.kardo.model.enums.DirectionEnum;
import ru.kardo.model.enums.EnumAuth;
import ru.kardo.service.ProfileService;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = {"http://localhost:3000", "https://kardo.zapto.org", "https://kardo-frontend.vercel.app", "http://51.250.32.102:8080"})
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/avatar/{avatarId}")
    public ResponseEntity<AvatarDtoResponse> getAvatar(@PathVariable Long avatarId) {
        log.info("GET: /profile/avatar/{}", avatarId);
        return ResponseEntity.ok().body(profileService.getAvatar(avatarId));
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

    @GetMapping()
    public ResponseEntity<List<ProfilePreviewDtoResponse>> getProfiles(@RequestParam(value = "from", defaultValue = "0")
                                                                           @PositiveOrZero Integer from,
                                                                       @RequestParam(value = "size", defaultValue = "5")
                                                                           @Positive Integer size) {
        log.info("GET: /profile?from={}&size={}", from, size);
        return ResponseEntity.ok().body(profileService.getProfiles(from, size));
    }

    @GetMapping("/about_staff")
    public List<ProfileAboutDto> getStaff(@RequestParam(required = false) Set<String> seasons,
                                          @RequestParam(required = false) Set<DirectionEnum> directions,
                                          @RequestParam(required = false) Set<EnumAuth> authorities,
                                          @RequestParam(required = false) Set<String> countries,
                                          @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                          @RequestParam(defaultValue = "5") @Positive Integer size) {
        return profileService.getStaffAndFacts(seasons, directions, authorities, countries, from, size);
    }

    @GetMapping("/kids_and_experts")
    public List<ProfileAboutDto> getKidsAndStaff(@RequestParam(required = false) Set<String> seasons,
                                                 @RequestParam(required = false) Set<DirectionEnum> directions,
                                                 @RequestParam(required = false) Set<EnumAuth> authorities,
                                                 @RequestParam(required = false) Set<String> countries,
                                                 @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                                 @RequestParam(defaultValue = "5") @Positive Integer size) {
        return profileService.getChildrenAndExperts(seasons, directions, authorities, countries, from, size);
    }

    @GetMapping("/staff-size")
    public ResponseEntity<Long> getStaffCount() {
        log.info("GET: /profile/staff-size");
        return ResponseEntity.ok().body(profileService.getStaffCount());
    }

    @GetMapping("/kids-and-staff-size")
    public ResponseEntity<Long> getKindsAndStaffCount() {
        log.info("GET: /profile/kids-and-staff-size");
        return ResponseEntity.ok().body(profileService.getKidsAndStaffCount());
    }
}
