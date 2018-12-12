package com.example.phonebook.service;

import com.example.phonebook.model.User;

public interface UserService {
    User get(int id);

    void delete(int id);

    void update(User user);

    User create(User user);
}
