package com.example.phonebook.web.phonebook;

import com.example.phonebook.model.Phonebook;
import com.example.phonebook.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import static com.example.phonebook.PhonebookTestData.*;
import static com.example.phonebook.TestUtil.*;
import static com.example.phonebook.UserTestData.USER;
import static com.example.phonebook.UserTestData.USER_ID;
import static com.example.phonebook.web.json.JsonUtil.writeValue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PhonebookRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = PhonebookRestController.REST_URL + '/';

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + PHONEBOOK_ID)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(contentJson(PHONEBOOK));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + PHONEBOOK_ID)
                .with(userHttpBasic(USER)))
                .andExpect(status().isNoContent());
        assertMatch(phonebookService.getAll(USER_ID), PHONEBOOK_2, PHONEBOOK_3, PHONEBOOK_4);
    }

    @Test
    void testUpdate() throws Exception {
        Phonebook updated = getUpdated();
        mockMvc.perform(put(REST_URL)
                .with(userHttpBasic(USER))
                .contentType(APPLICATION_JSON)
                .content(writeValue(updated)))
                .andExpect(status().isNoContent());
        assertMatch(phonebookService.get(PHONEBOOK_ID, USER_ID), updated);
    }

    @Test
    void testCreateWithLocation() throws Exception {
        Phonebook expected = getCreated();
        ResultActions action = mockMvc.perform(post(REST_URL)
                .with(userHttpBasic(USER))
                .contentType(APPLICATION_JSON)
                .content(writeValue(expected)))
                .andExpect(status().isCreated())
                .andDo(print());

        Phonebook returned = readFromJson(action, Phonebook.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(phonebookService.getAll(USER_ID), expected, PHONEBOOK, PHONEBOOK_2, PHONEBOOK_3, PHONEBOOK_4);
    }

    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(contentJson(PHONEBOOK_LIST));
    }
}