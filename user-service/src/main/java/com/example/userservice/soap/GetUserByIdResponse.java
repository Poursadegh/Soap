package com.example.userservice.soap;

public class GetUserByIdResponse {
    private UserType user;

    public GetUserByIdResponse() {
    }

    public GetUserByIdResponse(UserType user) {
        this.user = user;
    }

    public UserType getUser() {
        return user;
    }

    public void setUser(UserType user) {
        this.user = user;
    }
} 