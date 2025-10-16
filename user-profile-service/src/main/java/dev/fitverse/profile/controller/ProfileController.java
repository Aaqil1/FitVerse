package dev.fitverse.profile.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.fitverse.profile.dto.ProfileResponse;
import dev.fitverse.profile.dto.ProfileUpdateRequest;
import dev.fitverse.profile.service.ProfileService;
import dev.fitverse.profile.util.SecurityUtils;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/profiles")
@Validated
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/me")
    public ResponseEntity<ProfileResponse> getProfile(@AuthenticationPrincipal Jwt jwt) {
        Long userId = SecurityUtils.extractUserId(jwt);
        return ResponseEntity.ok(profileService.getProfileForUser(userId));
    }

    @PutMapping("/me")
    public ResponseEntity<ProfileResponse> updateProfile(@AuthenticationPrincipal Jwt jwt,
                                                         @Valid @RequestBody ProfileUpdateRequest request) {
        Long userId = SecurityUtils.extractUserId(jwt);
        return ResponseEntity.ok(profileService.updateProfile(userId, request));
    }
}
