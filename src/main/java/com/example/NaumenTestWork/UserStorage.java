package com.example.NaumenTestWork;

import java.util.List;

public interface UserStorage {

    public Long getUser(String name);

    public List<String> getAllStatistic ();

    public String getMaxAge();
}