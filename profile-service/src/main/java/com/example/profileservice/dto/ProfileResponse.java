package com.example.profileservice.dto;

import java.time.LocalDateTime;

public class ProfileResponse {

    private Long id;
    private Long userId;
    private String bio;
    private String location;
    private Integer age;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // User information from User Service
    private String userName;
    private String userEmail;

    // Default constructor
    public ProfileResponse() {
    }

    // Constructor with fields
    public ProfileResponse(Long id, Long userId, String bio, String location, Integer age, 
                         LocalDateTime createdAt, LocalDateTime updatedAt, String userName, String userEmail) {
        this.id = id;
        this.userId = userId;
        this.bio = bio;
        this.location = location;
        this.age = age;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userName = userName;
        this.userEmail = userEmail;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Override
    public String toString() {
        return "ProfileResponse{" +
                "id=" + id +
                ", userId=" + userId +
                ", bio='" + bio + '\'' +
                ", location='" + location + '\'' +
                ", age=" + age +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                '}';
    }
} 