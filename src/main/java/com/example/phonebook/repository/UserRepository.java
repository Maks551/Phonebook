package com.example.phonebook.repository;

import com.example.phonebook.model.User;

public interface UserRepository {
    User save(User user);

    boolean delete(int id);

    User get(int id);
}
