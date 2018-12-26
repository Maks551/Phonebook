package com.example.phonebook.service;

import com.example.phonebook.model.Role;
import com.example.phonebook.model.User;
import com.example.phonebook.repository.JpaUtil;
import com.example.phonebook.util.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DataAccessException;

import javax.validation.ConstraintViolationException;
import java.util.Collections;

import static com.example.phonebook.UserTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserServiceTest extends AbstractServiceTest {
    @Autowired
    private UserService service;

    @BeforeEach
    void setUp() throws Exception {
        cacheManager.getCache("users").clear();
        if (jpaUtil != null) {
            jpaUtil.clear2ndLevelHibernateCache();
        }
    }

    @Test
    void get() {
        assertMatch(service.get(USER_ID), USER);
    }

    @Test
    void getAll() {
        assertMatch(service.getAll(), USERS);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(0));
    }

    @Test
    void getByLogin() {
        assertMatch(service.getByLogin(USER_LOGIN), USER);
    }

    @Test
    void getByLoginNotFound() {
        assertThrows(NotFoundException.class, () -> service.getByLogin(""));
    }

    @Test
    void delete() {
        service.delete(USER_ID);
        assertMatch(service.getAll(), USER_2, USER_3, ADMIN);
    }

    @Test
    void deletedNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.delete(0));
    }

    @Test
    void update() {
        User updated = new User(USER);
        updated.setName("updatedName");
        updated.setLogin("updated");
        service.update(new User(updated));
        assertMatch(service.get(USER_ID), updated);
    }

    @Test
    void create() throws Exception {
        User newUser = getCreated();
        User created = service.create(new User(newUser));
        newUser.setId(created.getId());
        assertMatch(service.getAll(), USER, USER_2, USER_3, ADMIN, newUser);
    }

    @Test
    void duplicateLoginCreate() throws Exception {
        assertThrows(DataAccessException.class, () ->
                service.create(new User(null, USER_LOGIN, "newPass", "newName", Role.USER)));
    }

    @Test
    void testValidation() throws Exception {
        validateRootCause(() -> service.create(new User(null, " ", "password", "name", Role.USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new User(null, "a", "password", "name", Role.USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new User(null, "login", " ", "name", Role.USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new User(null, "login", "a", "name", Role.USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new User(null, "login", "password", " ", Role.USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new User(null, "login", "password", "a", Role.USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new User(null, "login", "password", "name", true, Collections.emptySet())), ConstraintViolationException.class);
    }
}