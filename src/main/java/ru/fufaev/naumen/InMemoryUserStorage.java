package ru.fufaev.naumen;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;



@Component
public class InMemoryUserStorage implements UserStorage {
    ConcurrentHashMap<String, Long> userMap;
    ConcurrentHashMap<String, Long> statistics = new ConcurrentHashMap<>();
    String fileName = "src/main/resources/user.txt";

    @PostConstruct
    public void readFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            userMap = readUsersFile(reader);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static ConcurrentHashMap<String, Long> readUsersFile(BufferedReader reader) throws Exception {
        String line = reader.readLine();

        String[] id_name = line.split(" ,");

        ConcurrentHashMap<String, Long> res = new ConcurrentHashMap<>();

        for (String std : id_name) {
            String[] str = std.split("_");
            res.put(str[0], Long.valueOf(str[1]));
        }

        return res;
    }

    @Override
    public long getUser(String name) {
        Random r = new Random();

        long age = userMap.computeIfAbsent(name, (key) -> r.nextLong(1, 100));

        statistics.compute(name, (key, value) -> value == null ? 1 : value + 1);

        return age;
    }

    @Override
    public List<String> getAllStatistic() {
        List<String> userList = new ArrayList<>();
        if (!statistics.isEmpty()) {
            statistics.forEach((key, value) -> userList.add(key + " " + value));
            return userList;
        }
        return userList;
    }

    @Override
    public String getMaxAge() {

        return Collections.max(userMap.entrySet(), Map.Entry.comparingByValue()).getKey();
    }
}