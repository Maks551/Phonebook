package com.example.phonebook.web.user;

import com.example.phonebook.model.User;
import com.example.phonebook.service.UserService;
import com.example.phonebook.to.UserTo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.example.phonebook.util.ValidationUtil.assureIdConsistent;
import static com.example.phonebook.util.ValidationUtil.checkNew;
import static com.example.phonebook.web.SecurityUtil.authUserId;

public class AbstractUserController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserService service;

    protected User get() {
        int id = authUserId();
        log.info("get {}", id);
        return service.get(id);
    }

    protected User getByLogin(String login) {
        log.info("getByLogin {}", login);
        return service.getByLogin(login);
    }

    protected List<User> getAll() {
        int id = authUserId();
        log.info("getAll for user {}", id);
        return service.getAll();
    }

    protected void delete() {
        int id = authUserId();
        log.info("delete {}", id);
        service.delete(id);
    }

    protected void update(User user, int id) {
        log.info("update {} with id={}", user, id);
        assureIdConsistent(user, id);
        service.update(user);
    }

    protected void update(UserTo userTo, int id) {
        log.info("update {} with id={}", userTo, id);
        assureIdConsistent(userTo, id);
        service.update(userTo);
    }

    protected User create(User user) {
        log.info("create {}", user);
        checkNew(user);
        return service.create(user);
    }
}
