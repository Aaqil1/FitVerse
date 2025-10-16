package dev.fitverse.profile.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import dev.fitverse.profile.domain.Gender;
import dev.fitverse.profile.dto.ProfileResponse;
import dev.fitverse.profile.dto.ProfileUpdateRequest;
import dev.fitverse.profile.exception.GlobalExceptionHandler;
import dev.fitverse.profile.service.ProfileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = ProfileController.class)
@Import(GlobalExceptionHandler.class)
class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProfileService profileService;

    @MockBean
    private JwtDecoder jwtDecoder;

    @Test
    void shouldReturnProfileForAuthenticatedUser() throws Exception {
        ProfileResponse response = new ProfileResponse();
        response.setUserId(42L);
        response.setGender(Gender.MALE);
        response.setHeightCm(new BigDecimal("180"));
        when(profileService.getProfileForUser(42L)).thenReturn(response);

        mockMvc.perform(get("/profiles/me")
                .with(jwt().jwt(jwt -> jwt.subject("42").claim("roles", "USER"))))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.userId").value(42));

        verify(profileService).getProfileForUser(42L);
    }

    @Test
    void shouldUpdateProfileForAuthenticatedUser() throws Exception {
        ProfileUpdateRequest request = new ProfileUpdateRequest();
        request.setGender(Gender.FEMALE);
        request.setAge(29);

        ProfileResponse response = new ProfileResponse();
        response.setUserId(42L);
        response.setGender(Gender.FEMALE);
        response.setAge(29);
        when(profileService.updateProfile(eq(42L), any(ProfileUpdateRequest.class))).thenReturn(response);

        mockMvc.perform(put("/profiles/me")
                .with(jwt().jwt(jwt -> jwt.subject("42").claim("roles", "USER")))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.gender").value("FEMALE"));

        verify(profileService).updateProfile(eq(42L), any(ProfileUpdateRequest.class));
    }
}
