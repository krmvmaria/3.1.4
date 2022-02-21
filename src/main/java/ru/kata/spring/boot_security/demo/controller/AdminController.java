package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.ArrayList;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping()
    public String userList(Model model) {
        model.addAttribute("allUsers", userService.listUsers());
        return "admin";
    }

    @DeleteMapping("/delete/{id}")
    public String  deleteUser(@PathVariable("id") long id) {
        userService.delete(id);
        return "redirect:/admin";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") long id){
        model.addAttribute("user", userService.findUserById(id));
        model.addAttribute("listRoles", roleService.getAllRoles());
        return "edit";
    }

    @PatchMapping("/{id}")
    public String update(User user, @RequestParam("listRoles") ArrayList<Long> roles){
        if (user.getPassword().equals(userService.findUserById(user.getId()).getPassword())){
            userService.change(user, roleService.findRoles(roles));
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.change(user, roleService.findRoles(roles));
        }
        return "redirect:/admin";
    }

    @GetMapping("/new")
    public String newUser (Model model){
        model.addAttribute("user", new User());
        model.addAttribute("listRoles", roleService.getAllRoles());
        return "/new";
    }

    @PostMapping("/new")
    public String addUser (User user, @RequestParam("listRoles") ArrayList<Long> roles){
        userService.add(user, roleService.findRoles(roles));
        return "redirect:/admin";
    }
}
