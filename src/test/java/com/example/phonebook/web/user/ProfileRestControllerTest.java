package com.example.phonebook.web.user;

import com.example.phonebook.model.User;
import com.example.phonebook.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import static com.example.phonebook.UserTestData.contentJson;
import static com.example.phonebook.TestUtil.readFromJson;
import static com.example.phonebook.TestUtil.userHttpBasic;
import static com.example.phonebook.UserTestData.*;
import static com.example.phonebook.web.json.JsonUtil.writeValue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProfileRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = ProfileRestController.REST_URL + '/';

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(contentJson(USER));
    }

    @Test
    void testGetByLogin() throws Exception {
        mockMvc.perform(get(REST_URL + "by?login=" + USER_LOGIN)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(contentJson(USER));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL)
                .with(userHttpBasic(USER)))
                .andExpect(status().isNoContent());
        assertMatch(userService.getAll(), USER_2, USER_3, ADMIN);
    }

    @Test
    void testUpdate() throws Exception {
        User updated = new User(USER);
        updated.setName("New User");
        mockMvc.perform(put(REST_URL)
                .with(userHttpBasic(USER))
                .contentType(APPLICATION_JSON)
                .content(writeValue(updated)))
                .andExpect(status().isOk());
        assertMatch(userService.get(USER_ID), updated);
    }

    @Test
    void testCreateWithLocation() throws Exception {
        User expected = getCreated();
        ResultActions action = mockMvc.perform(post(REST_URL)
                .with(userHttpBasic(USER))
                .contentType(APPLICATION_JSON)
                .content(jsonWithPassword(expected, CREATE_PASSWORD)))
                .andExpect(status().isCreated())
                .andDo(print());

        User returned = readFromJson(action, User.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(userService.getAll(), USER, USER_2, USER_3, ADMIN, expected);
    }
}