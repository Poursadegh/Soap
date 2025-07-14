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

    /**
     * Get user by ID via SOAP
     * @param userId the user ID
     * @return the user if found, null otherwise
     */
    public UserType getUserById(Long userId) {
        try {
            GetUserByIdRequest request = new GetUserByIdRequest();
            request.setId(userId);

            GetUserByIdResponse response = (GetUserByIdResponse) webServiceTemplate.marshalSendAndReceive(request);
            return response.getUser();
        } catch (Exception e) {
            // Log the error and return null if user not found or service unavailable
            System.err.println("Error calling User Service via SOAP: " + e.getMessage());
            return null;
        }
    }
} 