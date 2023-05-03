package ru.fufaev.naumen;

import java.util.List;

public interface UserStorage {

    long getUser(String name);

    List<String> getAllStatistic();

    String getMaxAge();
}