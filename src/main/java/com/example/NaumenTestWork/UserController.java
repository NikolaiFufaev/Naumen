package com.example.NaumenTestWork;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserStorage userStorage;
    private final UserService userService;

    @GetMapping
    public Long getUser(@RequestBody String name) {
        userService.readFile();
        return userStorage.getUser(name);
    }

    @GetMapping("/statistic")
    public List<String> getAllStatistic(){
        return userStorage.getAllStatistic();
    }

    @GetMapping("/maxAge")
    public String getMaxAge(){
        return userStorage.getMaxAge();
    }

}