package com.example.profileservice.service;

import com.example.profileservice.dto.ProfileRequest;
import com.example.profileservice.dto.ProfileResponse;
import com.example.profileservice.model.Profile;
import com.example.profileservice.repository.ProfileRepository;
import com.example.profileservice.soap.UserServiceClient;
import com.example.profileservice.soap.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserServiceClient userServiceClient;

    @Autowired
    public ProfileService(ProfileRepository profileRepository, UserServiceClient userServiceClient) {
        this.profileRepository = profileRepository;
        this.userServiceClient = userServiceClient;
    }

    /**
     * Create a new profile after validating user existence via SOAP
     * @param profileRequest the profile request data
     * @return the created profile response
     * @throws RuntimeException if user doesn't exist or profile already exists
     */
    public ProfileResponse createProfile(ProfileRequest profileRequest) {
        // Check if profile already exists for this user
        if (profileRepository.existsByUserId(profileRequest.getUserId())) {
            throw new RuntimeException("Profile already exists for user ID " + profileRequest.getUserId());
        }

        // Validate user exists via SOAP call to User Service
        UserType user = userServiceClient.getUserById(profileRequest.getUserId());
        if (user == null) {
            throw new RuntimeException("User with ID " + profileRequest.getUserId() + " not found");
        }

        // Create new profile
        Profile profile = new Profile(
                profileRequest.getUserId(),
                profileRequest.getBio(),
                profileRequest.getLocation(),
                profileRequest.getAge()
        );
        
        Profile savedProfile = profileRepository.save(profile);
        
        return convertToResponse(savedProfile, user);
    }

    /**
     * Get profile by ID with combined user data
     * @param id the profile ID
     * @return Optional containing the profile response if found
     */
    public Optional<ProfileResponse> getProfileById(Long id) {
        return profileRepository.findById(id)
                .map(profile -> {
                    // Get user data via SOAP
                    UserType user = userServiceClient.getUserById(profile.getUserId());
                    return convertToResponse(profile, user);
                });
    }

    /**
     * Convert Profile entity to ProfileResponse DTO with user data
     * @param profile the profile entity
     * @param user the user data from SOAP call
     * @return the profile response DTO
     */
    private ProfileResponse convertToResponse(Profile profile, UserType user) {
        String userName = user != null ? user.getName() : "Unknown";
        String userEmail = user != null ? user.getEmail() : "Unknown";
        
        return new ProfileResponse(
                profile.getId(),
                profile.getUserId(),
                profile.getBio(),
                profile.getLocation(),
                profile.getAge(),
                profile.getCreatedAt(),
                profile.getUpdatedAt(),
                userName,
                userEmail
        );
    }
} 