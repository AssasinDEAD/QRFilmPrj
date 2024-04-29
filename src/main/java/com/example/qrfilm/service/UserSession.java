package com.example.qrfilm.service;

import com.example.qrfilm.entity.SaveData;
import org.springframework.stereotype.Component;

@Component
public class UserSession {

    private SaveData currentUser;

    public SaveData getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(SaveData currentUser) {
        this.currentUser = currentUser;
    }
}
