package com.example.travel.model;

import java.time.LocalDateTime;

public class Reply {

    private String userId;
    private String userName;
    private String role; // USER or ADMIN
    private String message;

    private final LocalDateTime createdAt;

    public Reply() {
        this.createdAt = LocalDateTime.now();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
