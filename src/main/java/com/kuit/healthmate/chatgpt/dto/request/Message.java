package com.kuit.healthmate.chatgpt.dto.request;

import lombok.Getter;

@Getter
public class Message {

    private String role;
    private String content;

    public Message(String role, String content) {
        this.role = role;
        this.content = content;
    }

// constructor, getters and setters
}