package com.example.phonebook.repository.datajpa;

import com.example.phonebook.model.User;
import com.example.phonebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final CrudUserRepository repository;

    @Autowired
    public UserRepositoryImpl(CrudUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public boolean delete(int id) {
        return repository.delete(id) != 0;
    }

    @Override
    public User get(int id) {
        return repository.findById(id).orElse(null);
    }
}
