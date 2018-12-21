package com.example.phonebook.web.phonebook;

import com.example.phonebook.model.Phonebook;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = PhonebookRestController.REST_URL, produces = APPLICATION_JSON_VALUE)
public class PhonebookRestController extends AbstractPhonebookController{
    static final String REST_URL = "/rest/profile/phonebook";

    @GetMapping("/{id}")
    public Phonebook get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody Phonebook pbEntry) {
        super.update(pbEntry, pbEntry.getId());
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Phonebook> createWithLocation(@RequestBody Phonebook pbEntry) {
        Phonebook created = super.create(pbEntry);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Override
    @GetMapping()
    public List<Phonebook> getAll() {
        return super.getAll();
    }
}
