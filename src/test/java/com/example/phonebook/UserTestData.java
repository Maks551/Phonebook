package com.example.phonebook;

import com.example.phonebook.model.Role;
import com.example.phonebook.model.User;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.Arrays;
import java.util.List;

import static com.example.phonebook.web.json.JsonUtil.writeAdditionProps;
import static com.example.phonebook.web.json.JsonUtil.writeIgnoreProps;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

public class UserTestData {
    public static final int USER_ID = 1;
    public static final String USER_LOGIN = "login1";
    public static final String CREATE_PASSWORD = "created_password";

    public static final User USER = new User(USER_ID, USER_LOGIN, "password1", "name1", Role.USER);
    public static final User USER_2 = new User(USER_ID + 1, "login2", "password2", "name2", Role.USER);
    public static final User USER_3 = new User(USER_ID + 2, "login3", "password3", "name3", Role.USER);
    public static final User ADMIN = new User(USER_ID + 3, "admin", "admin", "admin", Role.ADMIN, Role.USER);

    public static final List<User> USERS = Arrays.asList(USER, USER_2, USER_3, ADMIN);

    public static void assertMatch(User actual, User expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "password", "phonebook");
    }

    public static void assertMatch(Iterable<User> actual, User... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<User> actual, Iterable<User> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("password", "phonebook").isEqualTo(expected);
    }

    public static User getCreated() {
        return new User(null, "created", CREATE_PASSWORD, "created_name", Role.USER);
    }

    public static ResultMatcher contentJson(User expected) {
        return content().json(writeIgnoreProps(expected, "password"));
    }

    public static String jsonWithPassword(User user, String passw) {
        return writeAdditionProps(user, "password", passw);
    }
}
