package com.example.phonebook.web.user;

import com.example.phonebook.AuthorizedUser;
import com.example.phonebook.model.User;
import com.example.phonebook.to.UserTo;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static com.example.phonebook.util.UserUtil.createNewFromTo;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = ProfileRestController.REST_URL, produces = APPLICATION_JSON_VALUE)
public class ProfileRestController extends AbstractUserController {
    static final String REST_URL = "/rest/profile";

    @GetMapping()
    @ResponseStatus(value = OK)
    public User get() {
        return super.get();
    }

    @GetMapping("/all")
    @ResponseStatus(OK)
    public List<User> getAll() {
        return super.getAll();
    }

    @GetMapping("/by")
    @ResponseStatus(OK)
    public User getByLogin(@RequestParam("login") String login) {
        return super.getByLogin(login);
    }

    @DeleteMapping
    @ResponseStatus(NO_CONTENT)
    public void delete() {
        super.delete();
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(NO_CONTENT)
    public void update(@RequestBody UserTo userTo, @AuthenticationPrincipal AuthorizedUser authUser) {
        super.update(userTo, authUser.getId());
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createWithLocation(@Valid @RequestBody User user) {
        User created = super.create(user);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public ResponseEntity<User> register(@Valid @RequestBody UserTo userTo) {
        User created = super.create(createNewFromTo(userTo));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

}
