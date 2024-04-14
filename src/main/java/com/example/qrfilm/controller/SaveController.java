package com.example.qrfilm.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.qrfilm.service.SaveService;

@Controller
public class SaveController {

    @Autowired
    private SaveService saveService;

    @GetMapping("/save")
    public String showSaveForm() {
        return "save";
    }

    @PostMapping("/save")
    public String saveData(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("password") String password) {
        saveService.saveData(name, email, password);
        return "redirect:/save?success";
    }
}
