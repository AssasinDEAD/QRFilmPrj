package com.example.qrfilm.service;

import com.example.qrfilm.entity.SaveData;
import com.example.qrfilm.repository.SaveDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private SaveDataRepository saveDataRepository;

    @Autowired
    private UserSession userSession;

    public SaveData findUserByEmailAndPassword(String email, String password) {
        return saveDataRepository.findByEmailAndPassword(email, password);
    }

    public SaveData getCurrentUser() {
        return userSession.getCurrentUser();
    }

    public void setCurrentUser(SaveData user) {
        userSession.setCurrentUser(user);
    }
}
