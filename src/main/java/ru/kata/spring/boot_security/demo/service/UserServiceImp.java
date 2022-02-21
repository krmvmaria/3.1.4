package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImp implements UserService, UserDetailsService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImp(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public void add(User user, Set<Role> roles) {
        user.setRoles(roles);
        userDao.add(user);
    }

    @Transactional
    @Override
    public void delete (long id) {
        userDao.delete(id);
    }

    @Transactional
    @Override
    public void change (User user, Set<Role> roles) {
        user.setRoles(roles);
        userDao.change(user, roles);
    }

    @Override
    public List<User> listUsers () {
        return userDao.listUsers();
    }

    @Override
    public User findUserById (long id) {
        return userDao.findUserById(id);
    }

    @Override
    public User findUserByName (String name) {
        return userDao.findUserByName(name);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDao.findUserByName(username);
    }
}
