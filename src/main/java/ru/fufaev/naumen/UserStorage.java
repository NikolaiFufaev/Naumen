package ru.fufaev.naumen;

import java.util.List;

public interface UserStorage {

    Long getUser(String name);

    List<User> getAllStatistic();

    User getMaxAge();
}