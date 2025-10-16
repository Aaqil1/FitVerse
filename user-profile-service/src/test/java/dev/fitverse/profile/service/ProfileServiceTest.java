package dev.fitverse.profile.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import dev.fitverse.profile.domain.Gender;
import dev.fitverse.profile.domain.Profile;
import dev.fitverse.profile.dto.ProfileResponse;
import dev.fitverse.profile.dto.ProfileUpdateRequest;
import dev.fitverse.profile.mapper.ProfileMapper;
import dev.fitverse.profile.repository.ProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private BodyMetricsCalculator bodyMetricsCalculator;

    private ProfileMapper profileMapper;

    @InjectMocks
    private ProfileService profileService;

    @BeforeEach
    void setUp() {
        profileMapper = Mappers.getMapper(ProfileMapper.class);
        profileService = new ProfileService(profileRepository, profileMapper, bodyMetricsCalculator);
    }

    @Test
    void shouldReturnProfileResponseWithBmi() {
        Profile profile = new Profile(1L);
        profile.setGender(Gender.MALE);
        profile.setHeightCm(new BigDecimal("180"));
        profile.setWeightKg(new BigDecimal("80"));

        when(profileRepository.findById(1L)).thenReturn(Optional.of(profile));
        when(bodyMetricsCalculator.calculateBodyMassIndex(any(), any()))
            .thenReturn(Optional.of(new BigDecimal("24.69")));

        ProfileResponse response = profileService.getProfileForUser(1L);

        assertThat(response.getBodyMassIndex()).isEqualByComparingTo("24.69");
        assertThat(response.getGender()).isEqualTo(Gender.MALE);
    }

    @Test
    void shouldUpdateProfileFields() {
        Profile profile = new Profile(2L);
        profile.setGender(Gender.UNSPECIFIED);
        when(profileRepository.findById(2L)).thenReturn(Optional.of(profile));
        when(profileRepository.save(any(Profile.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(bodyMetricsCalculator.calculateBodyMassIndex(any(), any())).thenReturn(Optional.empty());

        ProfileUpdateRequest request = new ProfileUpdateRequest();
        request.setAge(30);
        request.setGender(Gender.FEMALE);

        ProfileResponse response = profileService.updateProfile(2L, request);

        assertThat(response.getAge()).isEqualTo(30);
        assertThat(response.getGender()).isEqualTo(Gender.FEMALE);
    }
}
