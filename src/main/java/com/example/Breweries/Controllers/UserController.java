package com.example.Breweries.Controllers;

import com.example.Breweries.Daos.UserDao;
import com.example.Breweries.Models.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserDao userDao;

    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @GetMapping("/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return userDao.getUserByUsername(username);
    }

    @PostMapping
    public void createUser(@RequestBody User user) {
        userDao.createUser(user);
    }

    @PutMapping("/{username}")
    public void setUserPassword(@PathVariable String username, @RequestBody String password) {
        userDao.setUserPassword(username, password);
    }

    @DeleteMapping("/{username}")
    public void deleteUser(@PathVariable String username) {
        userDao.deleteUser(username);
    }

    @GetMapping("/{username}/roles")
    public List<String> getUserRoles(@PathVariable String username) {
        return userDao.getUserRoles(username);
    }

    @PostMapping("/{username}/roles")
    public void addUserRole(@PathVariable String username, @RequestBody String role) {
        userDao.addRoleToUser(username, role);
    }

    @DeleteMapping("/{username}/roles/{role}")
    public void deleteUserRole(@PathVariable String username, @PathVariable String role) {
        userDao.removeRoleFromUser(username, role);
    }
}
