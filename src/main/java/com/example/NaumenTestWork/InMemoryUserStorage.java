package com.example.NaumenTestWork;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


@Getter
@Component
public class InMemoryUserStorage implements UserStorage {
    UserService userService;
    @Autowired
    public InMemoryUserStorage( UserService userService) {
        this.userService = userService;
    }
    private final HashMap<String,Long> statistic = new HashMap<>();
    private long num = 0;

    @Override
    public Long getUser(String name) {
        Random r = new Random();
        if (userService.getMap().containsKey(name)) {
            statistic.put(name,++num);
            return userService.getMap().get(name);
        }
        return r.nextLong(1,1000000);
    }
    @Override
    public List<String> getAllStatistic(){
        List<String> userList = new ArrayList<>() ;
        if (!statistic.isEmpty()){
            statistic.entrySet().forEach(entry -> {
                userList.add(entry.getKey() + " " + entry.getValue());
            });
            return userList;
       }
        return userList;
    }

    @Override
    public String getMaxAge() {
        StringBuilder s = new StringBuilder(Collections.max(userService.getMap().entrySet(), Map.Entry.comparingByValue()).getKey());

        return s.toString();
    }
}