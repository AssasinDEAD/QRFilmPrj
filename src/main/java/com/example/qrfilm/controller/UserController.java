package com.example.qrfilm.controller;
import com.example.qrfilm.entity.SaveData;
import com.example.qrfilm.repository.SaveDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.example.qrfilm.repository.UserRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.HashMap;
import com.example.qrfilm.service.UserService;

import java.util.List;
@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private SaveDataRepository saveDataRepository;
    @Autowired
    private UserRepository userRepository;

    private SaveData currentUser;

    @GetMapping("/users")
    public String showUsers(Model model) {
        checkAuth(model);
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
    private void checkAuth(Model model) {
        currentUser = userService.getCurrentUser();
        System.out.println("Current user: " + currentUser); // Add this line
        if (currentUser == null) {
            model.addAttribute("error", "Вы не авторизованы");
            model.addAttribute("redirect", "/login");
            model.addAttribute("redirectParams", new HashMap<>());
        } else if (currentUser.getRole().equals("user") &&
                (model.getAttribute("redirect") == null ||
                        (!"/users".equals(model.getAttribute("redirect")) &&
                                !"/films/add".equals(model.getAttribute("redirect"))))) {
            model.addAttribute("error", "У вас недостаточно прав");
            model.addAttribute("redirect", "/films");
            model.addAttribute("redirectParams", new HashMap<>());
        }
    }


    @GetMapping("/users-list")
    public String getAllUsers(Model model) {
        checkAuth(model);
        List<SaveData> users = userRepository.findAll();
        model.addAttribute("users", users);
        model.addAttribute("currentUser", currentUser);
        return "users";
    }
}
