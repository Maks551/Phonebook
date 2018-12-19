package com.example.phonebook;

import com.example.phonebook.model.PhonebookEntry;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PhonebookTestData {
    public static final int PHONEBOOK_ID = 1;

    public static final PhonebookEntry PHONEBOOK = new PhonebookEntry(PHONEBOOK_ID, "LastName1", "FirstName1",
            "Surname1", "+380339833231", null, "Kiev", "email1@gmail.com");
    public static final PhonebookEntry PHONEBOOK_2 = new PhonebookEntry(PHONEBOOK_ID + 1, "LastName2", "FirstName2",
            "Surname2", "+380339833232", null, "Odessa", "email2@gmail.com");
    public static final PhonebookEntry PHONEBOOK_3 = new PhonebookEntry(PHONEBOOK_ID + 2, "LastName3", "FirstName3",
            "Surname3", "+380339833233", null, null, "email3@gmail.com");
    public static final PhonebookEntry PHONEBOOK_4 = new PhonebookEntry(PHONEBOOK_ID + 3, "LastName4", "FirstName4",
            "Surname4", "+380339833234", "0443515", "Kiev", null);

    public static final List<PhonebookEntry> PHONEBOOK_LIST = Arrays.asList(PHONEBOOK, PHONEBOOK_2, PHONEBOOK_3, PHONEBOOK_4);

    public static void assertMatch(PhonebookEntry actual, PhonebookEntry expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "user");
    }

    public static void assertMatch(Iterable<PhonebookEntry> actual, PhonebookEntry... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<PhonebookEntry> actual, Iterable<PhonebookEntry> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("user").isEqualTo(expected);
    }

    public static PhonebookEntry getCreated() {
        return new PhonebookEntry(null, "CreatedLastName", "CreatedFirstName",
                "CreatedSurename", "+380982344329",
                null, "new Address", "created@gmail.com");
    }

    public static PhonebookEntry getUpdated() {
        PhonebookEntry updated = new PhonebookEntry(PHONEBOOK);
        updated.setLastName("NewLastName");
        updated.setFirstName("NewFirstName");
        updated.setSurname("NewSurname");
        updated.setAddress("newAddress");
        updated.setEmail("newEmail@gmail.com");
        return updated;
    }
}
