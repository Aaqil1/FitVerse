package dev.fitverse.profile.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import dev.fitverse.profile.domain.Profile;
import dev.fitverse.profile.dto.ProfileResponse;
import dev.fitverse.profile.dto.ProfileUpdateRequest;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProfileMapper {

    ProfileResponse toResponse(Profile profile);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProfileFromRequest(ProfileUpdateRequest request, @MappingTarget Profile profile);
}
