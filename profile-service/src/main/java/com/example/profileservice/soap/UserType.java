package com.example.profileservice.soap;

import javax.xml.datatype.XMLGregorianCalendar;

public class UserType {
    private Long id;
    private String name;
    private String email;
    private XMLGregorianCalendar createdAt;
    private XMLGregorianCalendar updatedAt;

    public UserType() {
    }

    public UserType(Long id, String name, String email, XMLGregorianCalendar createdAt, XMLGregorianCalendar updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public XMLGregorianCalendar getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(XMLGregorianCalendar createdAt) {
        this.createdAt = createdAt;
    }

    public XMLGregorianCalendar getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(XMLGregorianCalendar updatedAt) {
        this.updatedAt = updatedAt;
    }
} 