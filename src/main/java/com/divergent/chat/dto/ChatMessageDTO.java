package com.divergent.chat.dto;

public class ChatMessageDTO {
    private String user;
    private String message;

    // Default constructor (required for JSON deserialization)
    public ChatMessageDTO() {
    }

    // Parameterized constructor
    public ChatMessageDTO(String user, String message) {
        this.user = user;
        this.message = message;
    }

    // Getters and setters
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
