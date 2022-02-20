package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
public class AdminController {

    private final UserService userService;

    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String userList(Model model) {
        model.addAttribute("allUsers", userService.listUsers());
        return "admin";
    }

    @DeleteMapping("admin/delete/{id}")
    public String  deleteUser(@PathVariable("id") long id) {
        userService.delete(id);
        return "redirect:/admin";
    }

    @GetMapping("admin/edit/{id}")
    public String edit(Model model, @PathVariable("id") long id){
        model.addAttribute("user", userService.findUserById(id));
        model.addAttribute("listRoles", roleService.getAllRoles());
        return "edit";
    }

    @PatchMapping("admin/edit")
    public String update(@Valid User user, @RequestParam("listRoles") ArrayList<Long> roles){
        userService.change(user, roleService.findRoles(roles));
        return "redirect:/admin";
    }

    @GetMapping("/admin/new")
    public String newUser (@ModelAttribute("user") User user){
        return "/new";
    }

    @PostMapping("/admin/new")
    public String addUser (@ModelAttribute("user") User userForm, Model model){
        userService.add(userForm);
        return "redirect:/admin";
    }
}
