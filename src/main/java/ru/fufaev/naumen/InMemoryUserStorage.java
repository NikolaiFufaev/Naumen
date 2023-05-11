package ru.fufaev.naumen;

import jakarta.annotation.PostConstruct;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class InMemoryUserStorage implements UserStorage {
    ConcurrentHashMap<String, Long> userMap;
    ConcurrentHashMap<String, Long> statistics = new ConcurrentHashMap<>();
    String fileName = "src/main/resources/user.txt";
    OkHttpClient client = new OkHttpClient.Builder().build();

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
    public Long getUser(String name) {
        if (name.isBlank()) {
            throw new ValidationException("String not be is empty");
        }
        long age = userMap.computeIfAbsent(name, this::randomAge);

        statistics.compute(name, (key, value) -> value == null ? 1 : value + 1);

        return age;
    }

    private long randomAge(String key) {
        Request request = new Request.Builder()
                .url("https://api.agify.io/?name=" + key)
                .build();

        Call call = client.newCall(request);
        Response response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JSONParser jsonParser = new JSONParser();
        JSONObject parse;
        try {
            parse = (JSONObject) jsonParser.parse(response.body().string());
        } catch (ParseException | IOException e) {
            throw new RuntimeException(e);
        }
        response.close();
        return (long) parse.get("age");
    }

    @Override
    public List<User> getAllStatistic() {
        List<User> userList = new ArrayList<>();
        statistics.forEach((key, value) -> userList.add(new User(key, value)));
        return userList;
    }

    @Override
    public User getMaxAge() {
        String name = Collections.max(userMap.entrySet(), Map.Entry.comparingByValue()).getKey();
        User user = new User(name, userMap.get(name));
        return user;
    }
}