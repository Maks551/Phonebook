package com.example.phonebook.web.json;

import com.example.phonebook.model.Phonebook;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.example.phonebook.PhonebookTestData.PHONEBOOK;
import static com.example.phonebook.PhonebookTestData.assertMatch;

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
}