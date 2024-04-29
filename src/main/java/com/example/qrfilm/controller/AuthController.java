package com.example.qrfilm.controller;

import com.example.qrfilm.entity.SaveData;
import com.example.qrfilm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.HashMap;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            Model model) {
        SaveData user = userService.findUserByEmailAndPassword(email, password);
        if (user != null) {
            userService.setCurrentUser(user);
            return "redirect:/films";
        } else {
            model.addAttribute("error", true);
            return "login";
        }
    }
    @GetMapping("/logout")
    public String logout() {
        userService.setCurrentUser(null);
        return "redirect:/login";
    }
    private void checkAuth(Model model) {
        SaveData currentUser = userService.getCurrentUser();
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
}
