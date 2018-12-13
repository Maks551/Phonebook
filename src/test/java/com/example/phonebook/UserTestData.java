package com.example.phonebook;

import com.example.phonebook.model.Role;
import com.example.phonebook.model.User;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTestData {
    public static final int USER_ID = 1;
    public static final String USER_LOGIN = "login1";

    public static final User USER = new User(USER_ID, USER_LOGIN, "password1", "name1", Role.ROLE_USER);
    public static final User USER_2 = new User(USER_ID + 1, "login2", "password2", "name2", Role.ROLE_USER);
    public static final User USER_3 = new User(USER_ID + 2, "login3", "password3", "name3", Role.ROLE_USER);

    public static final List<User> USERS = Arrays.asList(USER, USER_2, USER_3);

    public static void assertMatch(User actual, User expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "password", "phonebooks");
    }

    public static void assertMatch(Iterable<User> actual, User... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<User> actual, Iterable<User> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("password", "phonebooks").isEqualTo(expected);
    }

    public static User getCreated() {
        return new User(null, "CreatedUser", "CreatedPassword", "CreatedName", Role.ROLE_USER);
    }
}
