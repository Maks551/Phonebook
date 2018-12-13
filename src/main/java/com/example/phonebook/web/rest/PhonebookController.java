package com.example.phonebook.web.rest;

import com.example.phonebook.model.Phonebook;
import com.example.phonebook.service.PhonebookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.example.phonebook.util.ValidationUtil.checkNew;
import static com.example.phonebook.web.SecurityUtil.authUserId;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = PhonebookController.REST_URL, produces = APPLICATION_JSON_VALUE)
public class PhonebookController {
    static final String REST_URL = "/rest/profile/phonebook";
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final PhonebookService service;

    @Autowired
    public PhonebookController(PhonebookService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public Phonebook get(@PathVariable("id") int id) {
        int userId = authUserId();
        log.info("get phonebook {} for user {}", id, userId);
        return service.get(id, userId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        int userId = authUserId();
        log.info("delete phonebook {} for user {}", id, userId);
        service.delete(id, userId);
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody Phonebook phonebook) {
        int userId = authUserId();
        log.info("update {} for user {}", phonebook, userId);
        service.update(phonebook, userId);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Phonebook> createWithLocation(@RequestBody Phonebook phonebook) {
        int userId = authUserId();
        log.info("create {} for user {}", phonebook, userId);
        checkNew(phonebook);
        Phonebook created = service.create(phonebook, userId);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping()
    public List<Phonebook> getAll() {
        int userId = authUserId();
        log.info("getAll phonebooks for user {}", userId);
        return service.getAll(userId);
    }
}
