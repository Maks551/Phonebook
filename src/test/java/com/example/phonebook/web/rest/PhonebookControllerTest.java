package com.example.phonebook.web.rest;

import com.example.phonebook.model.Phonebook;
import com.example.phonebook.service.PhonebookService;
import com.example.phonebook.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import static com.example.phonebook.PhonebookTestData.*;
import static com.example.phonebook.TestUtil.contentJson;
import static com.example.phonebook.TestUtil.readFromJson;
import static com.example.phonebook.UserTestData.USER_ID;
import static com.example.phonebook.web.json.JsonUtil.writeValue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PhonebookControllerTest extends AbstractControllerTest {
    private static final String REST_URL = PhonebookController.REST_URL + '/';
    @Autowired
    private PhonebookService service;

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + PHONEBOOK_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(contentJson(PHONEBOOK));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + PHONEBOOK_ID))
                .andExpect(status().isNoContent());
        assertMatch(service.getAll(USER_ID), PHONEBOOK_2, PHONEBOOK_3, PHONEBOOK_4);
    }

    @Test
    void testUpdate() throws Exception {
        Phonebook updated = getUpdated();
        mockMvc.perform(put(REST_URL)
                .contentType(APPLICATION_JSON)
                .content(writeValue(updated)))
                .andExpect(status().isNoContent());
        assertMatch(service.get(PHONEBOOK_ID, USER_ID), updated);
    }

    @Test
    void testCreateWithLocation() throws Exception {
        Phonebook expected = getCreated();
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(APPLICATION_JSON)
                .content(writeValue(expected)))
                .andExpect(status().isCreated())
                .andDo(print());

        Phonebook returned = readFromJson(action, Phonebook.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(service.getAll(USER_ID), expected, PHONEBOOK, PHONEBOOK_2, PHONEBOOK_3, PHONEBOOK_4);
    }

    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(contentJson(PHONEBOOK_LIST));
    }
}