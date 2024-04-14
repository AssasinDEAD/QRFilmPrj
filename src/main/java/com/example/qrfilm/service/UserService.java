//package com.example.qrfilm.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import com.example.qrfilm.repository.UserRepository;
//import com.example.qrfilm.entity.User;
//import com.example.qrfilm.entity.Role;
//
//@Service
//public class UserService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    public User registerUser(User user) {
//        if (userRepository.findByEmail(user.getEmail()) != null) {
//            throw new RuntimeException("Электронная почта уже существует");
//        }
//        user.setRole(Role.USER);
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        return userRepository.save(user);
//    }
//}
