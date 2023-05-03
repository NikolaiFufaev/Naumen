package ru.fufaev.naumen;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public long getUser(@RequestBody String name) {
        return userStorage.getUser(name);
    }

    @GetMapping("/statistic")
    public List<String> getAllStatistic() {
        return userStorage.getAllStatistic();
    }

    @GetMapping("/maxAge")
    public String getMaxAge() {
        return userStorage.getMaxAge();
    }

}