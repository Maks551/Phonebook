package com.example.phonebook.web.phonebook;

import com.example.phonebook.model.Phonebook;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/ajax/profile/phonebooks")
public class AjaxPhonebookController extends AbstractPhonebookController {

    @Override
    @GetMapping(value = "/{id}")
    public Phonebook get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Phonebook> getAll() {
        return super.getAll();
    }

    @Override
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<String> createOrUpdate(@Valid Phonebook pbEntry) {
        if (pbEntry.isNew()) {
            super.create(pbEntry);
        } else {
            super.update(pbEntry, pbEntry.getId());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Phonebook> getFilter(
            @RequestParam(value = "lastNameF", required = false) String lastName,
            @RequestParam(value = "firstNameF", required = false) String firstName,
            @RequestParam(value = "mobilePhoneNumberF", required = false) String mobilePhoneNumber,
            @RequestParam(value = "homePhoneNumberF", required = false) String homePhoneNumber) {
        return super.getFilter(lastName, firstName, mobilePhoneNumber, homePhoneNumber);
    }
}
