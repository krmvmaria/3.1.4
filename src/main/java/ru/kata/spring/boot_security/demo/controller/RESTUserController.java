package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class RESTUserController {

    private final UserService userService;

    @Autowired
    public RESTUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public List<?> getUserByUsername (Principal principal) {
        User user = userService.findUserByName(principal.getName());
        List userInfo = new ArrayList<>();
        userInfo.add(user.getId());
        userInfo.add(user.getName());
        userInfo.add(user.getLastName());
        userInfo.add(user.getAge());
        userInfo.add(user.getRoles());
        return userInfo;
    }
}
