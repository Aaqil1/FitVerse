package dev.fitverse.profile.service;

import java.math.RoundingMode;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.fitverse.profile.domain.Gender;
import dev.fitverse.profile.domain.Profile;
import dev.fitverse.profile.dto.ProfileResponse;
import dev.fitverse.profile.dto.ProfileUpdateRequest;
import dev.fitverse.profile.mapper.ProfileMapper;
import dev.fitverse.profile.repository.ProfileRepository;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;
    private final BodyMetricsCalculator bodyMetricsCalculator;

    public ProfileService(ProfileRepository profileRepository,
                          ProfileMapper profileMapper,
                          BodyMetricsCalculator bodyMetricsCalculator) {
        this.profileRepository = profileRepository;
        this.profileMapper = profileMapper;
        this.bodyMetricsCalculator = bodyMetricsCalculator;
    }

    @Transactional(readOnly = true)
    public ProfileResponse getProfileForUser(Long userId) {
        Profile profile = profileRepository.findById(userId)
            .orElseGet(() -> initializeProfile(userId));
        return enrichResponse(profile);
    }

    @Transactional
    public ProfileResponse updateProfile(Long userId, ProfileUpdateRequest request) {
        Profile profile = profileRepository.findById(userId)
            .orElseGet(() -> initializeProfile(userId));

        profileMapper.updateProfileFromRequest(request, profile);
        if (profile.getGender() == null) {
            profile.setGender(Gender.UNSPECIFIED);
        }

        Profile saved = profileRepository.save(profile);
        return enrichResponse(saved);
    }

    private Profile initializeProfile(Long userId) {
        Profile profile = new Profile(userId);
        profile.setGender(Gender.UNSPECIFIED);
        profile.setGoal("BALANCED");
        return profileRepository.save(profile);
    }

    private ProfileResponse enrichResponse(Profile profile) {
        ProfileResponse response = profileMapper.toResponse(profile);
        bodyMetricsCalculator.calculateBodyMassIndex(profile.getHeightCm(), profile.getWeightKg())
            .map(bmi -> bmi.setScale(2, RoundingMode.HALF_UP))
            .ifPresent(response::setBodyMassIndex);
        return response;
    }
}
