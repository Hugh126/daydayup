package com.example.myspring;

import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService{

    @Override
    public User getUserById(long userId) {
        return User.data.get(userId);
    }

    @Override
    public void updateUser(User user) {
        User.data.put(user.getUserId(),user);
    }

    @Override
    public void deleteUser(long userId) {
        User.data.remove(userId);
    }
}
