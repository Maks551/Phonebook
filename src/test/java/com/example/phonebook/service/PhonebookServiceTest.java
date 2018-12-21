package com.example.phonebook.service;

import com.example.phonebook.model.Phonebook;
import com.example.phonebook.util.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;

import javax.validation.ConstraintViolationException;

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
        jpaUtil.clear2ndLevelHibernateCache();
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
        Phonebook updated = new Phonebook(PHONEBOOK);
        updated.setLastName("NewLastName");
        updated.setFirstName("NewFirstName");
        updated.setSurname("NewSurname");
        updated.setHomePhoneNumber("035 99 109 11");
        service.update(new Phonebook(updated), USER_ID);
        assertMatch(service.get(PHONEBOOK_ID ,USER_ID), updated);
    }

    @Test
    void create() {
        Phonebook pbEntry = new Phonebook(null, "newLastName", "newFirstName", "newSurname", "+380339833212", null, null, "newEmail@gmail.com");
        Phonebook created = service.create(new Phonebook(pbEntry), USER_ID);
        pbEntry.setId(created.getId());
        assertMatch(service.getAll(USER_ID), PHONEBOOK, PHONEBOOK_2, PHONEBOOK_3, PHONEBOOK_4, created);
    }

    @Test
    void getAll() {
        assertMatch(service.getAll(USER_ID), PHONEBOOK_LIST);
    }

    @Test
    void testValidation() throws Exception {
        validateRootCause(() -> service.create(new Phonebook(null, null, "FirstName", "Surname", "380339833231", "035 99 109 11", "Kiev", "test@gmail.com"), USER_ID), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Phonebook(null, "a", "FirstName", "Surname", "380339833231", "035 99 109 11", "Kiev", "test@gmail.com"), USER_ID), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Phonebook(null, "LastName", null, "Surname", "380339833231", "035 99 109 11", "Kiev", "test@gmail.com"), USER_ID), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Phonebook(null, "LastName", "a", "Surname", "380339833231", "035 99 109 11", "Kiev", "test@gmail.com"), USER_ID), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Phonebook(null, "LastName", "FirstName", null, "380339833231", "035 99 109 11", "Kiev", "test@gmail.com"), USER_ID), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Phonebook(null, "LastName", "FirstName", "a", "380339833231", "035 99 109 11", "Kiev", "test@gmail.com"), USER_ID), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Phonebook(null, "LastName", "FirstName", "Surname", null, "035 99 109 11", "Kiev", "test@gmail.com"), USER_ID), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Phonebook(null, "LastName", "FirstName", "Surname", "12312", "035 99 109 11", "Kiev", "test@gmail.com"), USER_ID), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Phonebook(null, "LastName", "FirstName", "Surname", "380339833231", "109 11", "Kiev", "test@gmail.com"), USER_ID), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Phonebook(null, "LastName", "FirstName", "Surname", "380339833231", "035 99 109 11", "Kiev", "@gmail.com"), USER_ID), ConstraintViolationException.class);
    }
}