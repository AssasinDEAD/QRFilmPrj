package com.example.qrfilm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.qrfilm.repository.SaveRepository;
import com.example.qrfilm.entity.SaveData;
@Service
public class SaveService {

    @Autowired
    private SaveRepository saveRepository;

    public void saveData(String name, String email, String password) {
        SaveData saveData = new SaveData();
        saveData.setName(name);
        saveData.setEmail(email);
        saveData.setPassword(password);
        saveRepository.save(saveData);
    }
}
