package com.example.NaumenTestWork;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

@Service
@Slf4j
public class UserService {
    HashMap<String, Long> users;
    String fileName = "src/main/resources/user.txt";

    @PostConstruct
    public void readFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            users = readHashMap(reader);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public HashMap<String, Long> getMap() {
        return users;
    }

    private static HashMap<String, Long> readHashMap(BufferedReader reader) throws Exception {
        String line = reader.readLine();

        String[] id_name = line.split(" ,");

        HashMap<String, Long> res = new HashMap<>();

        for (String std : id_name) {
            String[] str = std.split("_");
            res.put(str[0], Long.valueOf(str[1]));
        }

        return res;
    }
}