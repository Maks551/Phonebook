package com.example.phonebook.service;

import com.example.phonebook.model.User;
import com.example.phonebook.repository.UserRepository;
import com.example.phonebook.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static com.example.phonebook.util.ValidationUtil.checkNotFound;
import static com.example.phonebook.util.ValidationUtil.checkNotFoundWithId;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Cacheable("users")
    @Override
    public User get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id), id);
    }

    @Override
    public User getByLogin(String login) throws NotFoundException {
        Assert.notNull(login, "login must not be null");
        return checkNotFound(repository.getByLogin(login), "login=" + login);
    }

    @CacheEvict(value = "users", allEntries = true)
    @Override
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id), id);
    }

    @CacheEvict(value = "users", allEntries = true)
    @Override
    public void update(User user) {
        checkNotFoundWithId(repository.save(user), user.getId());
    }

    @CacheEvict(value = "users", allEntries = true)
    @Override
    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        return repository.save(user);
    }

    @Cacheable("users")
    @Override
    public List<User> getAll() {
        return repository.getAll();
    }
}
