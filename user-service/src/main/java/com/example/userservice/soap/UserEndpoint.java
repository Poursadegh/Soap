package com.example.userservice.soap;

import com.example.userservice.model.User;
import com.example.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.ZoneId;
import java.util.GregorianCalendar;

@Endpoint
public class UserEndpoint {

    private static final String NAMESPACE_URI = "http://example.com/users";

    private final UserService userService;

    @Autowired
    public UserEndpoint(UserService userService) {
        this.userService = userService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUserByIdRequest")
    @ResponsePayload
    public GetUserByIdResponse getUserById(@RequestPayload GetUserByIdRequest request) {
        GetUserByIdResponse response = new GetUserByIdResponse();
        
        User user = userService.getUserByIdForSoap(request.getId());
        
        if (user != null) {
            UserType userType = new UserType();
            userType.setId(user.getId());
            userType.setName(user.getName());
            userType.setEmail(user.getEmail());
            userType.setCreatedAt(toXmlGregorianCalendar(user.getCreatedAt()));
            if (user.getUpdatedAt() != null) {
                userType.setUpdatedAt(toXmlGregorianCalendar(user.getUpdatedAt()));
            }
            response.setUser(userType);
        }
        
        return response;
    }

    private XMLGregorianCalendar toXmlGregorianCalendar(java.time.LocalDateTime localDateTime) {
        try {
            GregorianCalendar calendar = GregorianCalendar.from(localDateTime.atZone(ZoneId.systemDefault()));
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
        } catch (Exception e) {
            throw new RuntimeException("Error converting LocalDateTime to XMLGregorianCalendar", e);
        }
    }
} 