package com.example.phonebook;

import com.example.phonebook.model.Phonebook;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PhonebookTestData {
    public static final int PHONEBOOK_ID = 1;

    public static final Phonebook PHONEBOOK = new Phonebook(PHONEBOOK_ID, "LastName1", "FirstName1",
            "Surname1", "+380339833231", "", "Kiev", "email1@gmail.com");
    public static final Phonebook PHONEBOOK_2 = new Phonebook(PHONEBOOK_ID + 1, "LastName2", "FirstName2",
            "Surname2", "+380339833232", "", "Odessa", "email2@gmail.com");
    public static final Phonebook PHONEBOOK_3 = new Phonebook(PHONEBOOK_ID + 2, "LastName3", "FirstName3",
            "Surname3", "+380339833233", "", "", "email3@gmail.com");
    public static final Phonebook PHONEBOOK_4 = new Phonebook(PHONEBOOK_ID + 3, "LastName4", "FirstName4",
            "Surname4", "+380339833234", "0443515", "Kiev", "");

    public static final List<Phonebook> PHONEBOOK_LIST = Arrays.asList(PHONEBOOK, PHONEBOOK_2, PHONEBOOK_3, PHONEBOOK_4);

    public static void assertMatch(Phonebook actual, Phonebook expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "user");
    }

    public static void assertMatch(Iterable<Phonebook> actual, Phonebook... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Phonebook> actual, Iterable<Phonebook> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("user").isEqualTo(expected);
    }

    public static Phonebook getCreated() {
        return new Phonebook(null, "CreatedLastName", "CreatedFirstName",
                "CreatedSurename", "+380982344329",
                null, "new Address", "created@gmail.com");
    }

    public static Phonebook getUpdated() {
        Phonebook updated = new Phonebook(PHONEBOOK);
        updated.setLastName("NewLastName");
        updated.setFirstName("NewFirstName");
        updated.setSurname("NewSurname");
        updated.setAddress("newAddress");
        updated.setEmail("newEmail@gmail.com");
        return updated;
    }
}
