package com.example.qrfilm.controller;
import com.example.qrfilm.entity.SaveData;
import com.example.qrfilm.repository.SaveDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private SaveDataRepository saveDataRepository;

    @GetMapping("/users")
    public String showUsers(Model model) {
        List<SaveData> users = saveDataRepository.findAll();
        model.addAttribute("users", users);
        return "users";
    }

    @PostMapping("/ban-user")
    public String banUser(@RequestParam Long userId) {
        SaveData user = saveDataRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getBan().equals("Not Banned")) {
            user.setBan("Banned");
        } else {
            user.setBan("Not Banned");
        }
        saveDataRepository.save(user); // сохраняем изменения в базе данных
        return "redirect:/users";
    }


}
