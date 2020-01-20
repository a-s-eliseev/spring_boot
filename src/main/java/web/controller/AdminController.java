package web.controller;

import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;

import java.util.List;

@RestController
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/admin")
    public List<User> getListUsers() {
        return userService.listUsers();
    }

    @GetMapping(value = "/admin/{id}")
    public User getUser(@PathVariable long id) {
        return userService.getUserById(id);
    }

    @PutMapping(value = "/admin/addUser")
    public void addUserPut(@RequestBody User user) {
        userService.addUser(user);
    }

    @PatchMapping(value = "/admin/editUser")
    public void editUser(@RequestBody User user) {
        userService.editUser(user);
    }

    @DeleteMapping(value = "/admin/deleteUser/{id}")
    public void deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
    }
}
