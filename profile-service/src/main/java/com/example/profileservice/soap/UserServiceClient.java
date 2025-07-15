package com.example.profileservice.soap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

@Service
public class UserServiceClient {

    private final WebServiceTemplate webServiceTemplate;

    @Autowired
    public UserServiceClient(WebServiceTemplate webServiceTemplate) {
        this.webServiceTemplate = webServiceTemplate;
    }

    public UserType getUserById(Long userId) {
        try {
            GetUserByIdRequest request = new GetUserByIdRequest();
            request.setId(userId);

            GetUserByIdResponse response = (GetUserByIdResponse) webServiceTemplate.marshalSendAndReceive(request);
            return response.getUser();
        } catch (Exception e) {
            System.err.println("Error calling User Service via SOAP: " + e.getMessage());
            return null;
        }
    }
} 