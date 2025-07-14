package com.example.profileservice.repository;

import com.example.profileservice.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    /**
     * Find profile by user ID
     * @param userId the user ID to search for
     * @return Optional containing the profile if found
     */
    Optional<Profile> findByUserId(Long userId);

    /**
     * Check if profile exists by user ID
     * @param userId the user ID to check
     * @return true if profile exists, false otherwise
     */
    boolean existsByUserId(Long userId);
} 