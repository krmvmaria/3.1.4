package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import java.util.List;

@RestController
@RequestMapping("api/admin")
public class RESTAdminController {

    private final UserService userService;

    @Autowired
    public RESTAdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public List<User> userList(){
        return userService.listUsers();
    }

    @GetMapping("/{id}")
    public User showUser(@PathVariable long id) {
        return userService.findUserById(id);
    }

    @PostMapping()
    public List<User> addUser(@RequestBody User user){
        userService.add(user, user.getRoles());
        return userService.listUsers();
    }

    @PutMapping()
    public User update(@RequestBody User user){
        userService.change(user, user.getRoles());
        return user;
    }

    @DeleteMapping("/{id}")
    public List<User> deleteUser(@PathVariable long id) {
        userService.delete(id);
        return userService.listUsers();
    }

}
