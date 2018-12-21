package com.example.phonebook.web.json;

import com.example.phonebook.UserTestData;
import com.example.phonebook.model.Phonebook;
import com.example.phonebook.model.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.example.phonebook.PhonebookTestData.PHONEBOOK;
import static com.example.phonebook.PhonebookTestData.assertMatch;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonUtilTest {

    @Test
    void testReadWriteValue() throws Exception {
        String json = JsonUtil.writeValue(PHONEBOOK);
        System.out.println(json);
        Phonebook phonebook = JsonUtil.readValue(json, Phonebook.class);
        assertMatch(phonebook, PHONEBOOK);
    }

    @Test
    void testReadWriteValues() throws Exception {
        String json = JsonUtil.writeValue(PHONEBOOK);
        System.out.println(json);
        List<Phonebook> phonebooks = JsonUtil.readValues(json, Phonebook.class);
        assertMatch(phonebooks, PHONEBOOK);
    }

    @Test
    void testWriteOnlyAccess() throws Exception {
        String json = JsonUtil.writeValue(UserTestData.USER);
        System.out.println(json);
        assertThat(json, not(containsString("password")));
        String jsonWithPass = UserTestData.jsonWithPassword(UserTestData.USER, "newPass");
        System.out.println(jsonWithPass);
        User user = JsonUtil.readValue(jsonWithPass, User.class);
        assertEquals(user.getPassword(), "newPass");
    }
}