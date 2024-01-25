package com.example.myspring;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class User implements Serializable {

    public static Map<Long, User> data = new HashMap(){{
        put(111L, new User(111, "aa"));
        put(222L, new User(222L, "bb"));
        put(333L, new User(333L, "cc"));
    }};

    private long  userId;
    private String name;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User() {
    }

    public User(long userId, String name) {
        this.userId = userId;
        this.name = name;
    }
}
