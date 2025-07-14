package com.example.userservice.soap;

public class GetUserByIdRequest {
    private Long id;

    public GetUserByIdRequest() {
    }

    public GetUserByIdRequest(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
} 