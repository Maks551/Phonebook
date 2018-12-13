package com.example.phonebook.service;

import com.example.phonebook.model.User;
import com.example.phonebook.util.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DataAccessException;

import static com.example.phonebook.UserTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserServiceTest extends AbstractServiceTest{
    @Autowired
    private UserService service;

    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    void setUp() throws Exception {
        cacheManager.getCache("users").clear();
    }

    @Test
    void get() {
        assertMatch(service.get(USER_ID), USER);
    }

    @Test
    void getNotFound() {
        System.out.println(service.get(USER_ID));
        assertThrows(NotFoundException.class, () -> service.get(0));
    }

    @Test
    void getByLogin() {
        System.out.println(service.get(USER_ID));
        assertMatch(service.getByLogin(USER_LOGIN), USER);
    }

    @Test
    void getByLoginNotFound() {
        System.out.println(service.get(USER_ID));
        assertThrows(NotFoundException.class, () -> service.getByLogin(""));
    }

    @Test
    void delete() {
        System.out.println(service.get(USER_ID));
        service.delete(USER_ID);
        assertMatch(service.getAll(), USER_2, USER_3);
    }

    @Test
    void deletedNotFound() throws Exception {
        System.out.println(service.get(USER_ID));
        assertThrows(NotFoundException.class, () -> service.delete(0));
    }

    @Test
    void update() {
        System.out.println(service.get(USER_ID));
        User updated = new User(USER);
        updated.setName("UpdatedName");
        updated.setLogin("UpdatedLogin");
        service.update(new User(updated));
        assertMatch(service.get(USER_ID), updated);
    }

    @Test
    void create() throws Exception {
        System.out.println(service.get(USER_ID));
        User newUser = new User(null, "newLogin", "passwordNew", "newUser");
        User created = service.create(new User(newUser));
        newUser.setId(created.getId());
        assertMatch(service.getAll(), USER, USER_2, USER_3, newUser);
    }

    @Test
    void duplicateLoginCreate() throws Exception {
        System.out.println(service.get(USER_ID));
        assertThrows(DataAccessException.class, () ->
                service.create(new User(null, USER_LOGIN, "newPass", "newName")));
    }
}