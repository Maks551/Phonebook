package com.example.phonebook.web.rest;

import com.example.phonebook.model.User;
import com.example.phonebook.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static com.example.phonebook.util.ValidationUtil.assureIdConsistent;
import static com.example.phonebook.util.ValidationUtil.checkNew;
import static com.example.phonebook.web.SecurityUtil.authUserId;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = ProfileRestController.REST_URL, produces = APPLICATION_JSON_VALUE)
public class ProfileRestController {
    static final String REST_URL = "/rest/profile";
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final UserService service;

    @Autowired
    public ProfileRestController(UserService service) {
        this.service = service;
    }

    @GetMapping()
    public User get() {
        int id = authUserId();
        log.info("get {}", id);
        return service.get(id);
    }

    @GetMapping("/by")
    public User getByLogin(@RequestParam("email") String login) {
        log.info("getByLogin {}", login);
        return service.getByLogin(login);
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete() {
        int id = authUserId();
        log.info("delete {}", id);
        service.delete(id);
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    public void update(@RequestBody User user) {
        int id = authUserId();
        log.info("update {} with id={}", user, id);
        assureIdConsistent(user, id);
        service.update(user);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createWithLocation(@RequestBody User user) {
        log.info("create {}", user);
        checkNew(user);
        User created = service.create(user);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
