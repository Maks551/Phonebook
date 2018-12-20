package com.example.phonebook.service;

import com.example.phonebook.model.PhonebookEntry;
import com.example.phonebook.util.exception.NotFoundException;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolationException;
import java.time.Month;

import static com.example.phonebook.PhonebookTestData.*;
import static com.example.phonebook.UserTestData.USER_ID;
import static java.time.LocalDateTime.of;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PhonebookServiceTest extends AbstractServiceTest{
    @Autowired
    private PhonebookService service;

    @BeforeEach
    void setUp() throws Exception {
        cacheManager.getCache("phonebooks").clear();
    }

    @Test
    void get() {
        assertMatch(service.get(PHONEBOOK_ID, USER_ID), PHONEBOOK);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(0, USER_ID));
    }

    @Test
    void delete() {
        service.delete(PHONEBOOK_ID, USER_ID);
        assertMatch(service.getAll(USER_ID), PHONEBOOK_2, PHONEBOOK_3, PHONEBOOK_4);
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(0, USER_ID));
    }

    @Test
    void update() {
        PhonebookEntry updated = new PhonebookEntry(PHONEBOOK);
        updated.setLastName("NewLastName");
        updated.setFirstName("NewFirstName");
        updated.setSurname("NewSurname");
        updated.setHomePhoneNumber("+380655888412");
        service.update(new PhonebookEntry(updated), USER_ID);
        assertMatch(service.get(PHONEBOOK_ID ,USER_ID), updated);
    }

    @Test
    void create() {
        PhonebookEntry pbEntry = new PhonebookEntry(null, "newLastName", "newFirstName", "newSurname", "+380339833212", null, "Kiev", "newEmail@gmail.com");
        PhonebookEntry created = service.create(new PhonebookEntry(pbEntry), USER_ID);
        pbEntry.setId(created.getId());
        assertMatch(service.getAll(USER_ID), PHONEBOOK, PHONEBOOK_2, PHONEBOOK_3, PHONEBOOK_4, created);
    }

    @Test
    void getAll() {
        assertMatch(service.getAll(USER_ID), PHONEBOOK_LIST);
    }

//    @Test
//    void testValidation() throws Exception {
//        validateRootCause(() -> service.create(new PhonebookEntry(null, of(2015, Month.JUNE, 1, 18, 0), "  ", 300), USER_ID), ConstraintViolationException.class);
//        validateRootCause(() -> service.create(new Meal(null, null, "Description", 300), USER_ID), ConstraintViolationException.class);
//        validateRootCause(() -> service.create(new Meal(null, of(2015, Month.JUNE, 1, 18, 0), "Description", 9), USER_ID), ConstraintViolationException.class);
//        validateRootCause(() -> service.create(new Meal(null, of(2015, Month.JUNE, 1, 18, 0), "Description", 5001), USER_ID), ConstraintViolationException.class);
//    }
}