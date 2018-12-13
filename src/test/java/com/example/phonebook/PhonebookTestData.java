package com.example.phonebook;

import com.example.phonebook.model.Phonebook;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PhonebookTestData {
    public static final int PHONEBOOK_ID = 1;

    public static final Phonebook PHONEBOOK = new Phonebook(PHONEBOOK_ID, "LastName1", "FirstName1",
            "Surname1", "+380339833231", null, "Kiev", "email1@gmail.com");
    public static final Phonebook PHONEBOOK_2 = new Phonebook(PHONEBOOK_ID + 1, "LastName2", "FirstName2",
            "Surname2", "+380339833232", null, "Odessa", "email2@gmail.com");
    public static final Phonebook PHONEBOOK_3 = new Phonebook(PHONEBOOK_ID + 2, "LastName3", "FirstName3",
            "Surname3", "+380339833233", null, null, "email3@gmail.com");
    public static final Phonebook PHONEBOOK_4 = new Phonebook(PHONEBOOK_ID + 3, "LastName4", "FirstName4",
            "Surname4", "+380339833234", "0443515", "Kiev", null);
//    public static final Phonebook PHONEBOOK_5 = new Phonebook(PHONEBOOK_ID + 4, "LastName5", "FirstName5", "Surname5", "+380339833235", "0443598", null, "email5@gmail.com");
//    public static final Phonebook PHONEBOOK_6 = new Phonebook(PHONEBOOK_ID + 5, "LastName6", "FirstName6", "Surname6", "+380339833236", null, "Kiev", "email6@gmail.com");
//    public static final Phonebook PHONEBOOK_7 = new Phonebook(PHONEBOOK_ID + 6, "LastName7", "FirstName7", "Surname7", "+380339833237", "0435576", "Odessa", "email7@gmail.com");
//    public static final Phonebook PHONEBOOK_8 = new Phonebook(PHONEBOOK_ID + 7, "LastName8", "FirstName8", "Surname8", "+380339833238", null, null, null);
//    public static final Phonebook PHONEBOOK_9 = new Phonebook(PHONEBOOK_ID + 8, "LastName9", "FirstName9", "Surname9", "+380339833239", "0443576", "Kiev", "email9@gmail.com");

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
}
