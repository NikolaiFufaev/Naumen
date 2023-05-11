package ru.fufaev.naumen;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserStorage userStorage;

    public UserController(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @GetMapping
    public Long getUser(@RequestParam(required = false) String name) {
        return userStorage.getUser(name);
    }

    @GetMapping("/statistic")
    public List<User> getAllStatistic() {
        return userStorage.getAllStatistic();
    }

    @GetMapping("/maxAge")
    public User getMaxAge() {
        return userStorage.getMaxAge();
    }

}