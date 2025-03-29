package adapter.controller;

import application.service.UserService;
import domain.model.User;
import adapter.mapper.UserRequest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public Optional<User> getUser(@PathVariable Long id) {
    // public User getUser(@PathVariable Long id) {
        return this.userService.getUserById(id);
    }

    @PostMapping
    public void createUser(@RequestBody UserRequest request) {
        this.userService.createUser(request.getName(), request.getEmail());
    }
}
