package com.example.phonebook.service;

import com.example.phonebook.model.User;
import com.example.phonebook.util.exception.NotFoundException;

import java.util.List;

public interface UserService {
    User get(int id) throws NotFoundException;

    User getByLogin(String login) throws NotFoundException;

    void delete(int id) throws NotFoundException;

    void update(User user);

    User create(User user);

    List<User> getAll();
}
