package com.bengisu.dao;

import com.bengisu.user.User;

public interface UserDAO
{
    void save(User user);
    void findAll();
    void update(User user);
    void delete(int id);
}
