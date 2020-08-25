package com.example.SmartGuidance_App.chat_classes;

public class ResponseMessage {
    String text;

    boolean isUser;

    public ResponseMessage(String text, boolean isUser) {
        this.text = text;
        this.isUser = isUser;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isUser() {
        return isUser;
    }

    public void setUser(boolean User) {
        isUser = User;
    }
}
