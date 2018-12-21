package com.example.phonebook.web.user;

import com.example.phonebook.model.Role;
import com.example.phonebook.model.User;
import com.example.phonebook.to.UserTo;
import com.example.phonebook.util.exception.ErrorType;
import com.example.phonebook.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static com.example.phonebook.UserTestData.contentJson;
import static com.example.phonebook.TestUtil.readFromJson;
import static com.example.phonebook.TestUtil.userHttpBasic;
import static com.example.phonebook.UserTestData.*;
import static com.example.phonebook.util.UserUtil.createNewFromTo;
import static com.example.phonebook.util.UserUtil.updateFromTo;
import static com.example.phonebook.web.ExceptionInfoHandler.EXCEPTION_DUPLICATE_LOGIN;
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
    void testGetUnAuth() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testGetForbidden() throws Exception {
        mockMvc.perform(get(REST_URL + "/all")
                .with(userHttpBasic(USER)))
                .andExpect(status().isForbidden());
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
        UserTo updatedTo = new UserTo(null, "newName", "newlogin", "newPassword");

        mockMvc.perform(put(REST_URL)
                .contentType(APPLICATION_JSON)
                .with(userHttpBasic(USER))
                .content(writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertMatch(userService.getByLogin("newlogin"), updateFromTo(new User(USER), updatedTo));
    }

    @Test
    void testUpdateInvalid() throws Exception {
        UserTo updatedTo = new UserTo(null, null, "lo", null);

        mockMvc.perform(put(REST_URL)
                .with(userHttpBasic(USER))
                .contentType(APPLICATION_JSON)
                .content(writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(ErrorType.VALIDATION_ERROR))
                .andDo(print());
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

    @Test
    void testCreateInvalid() throws Exception {
        User expected = new User(null, "", "newPass", null, Role.USER);

        mockMvc.perform(post(REST_URL)
                .contentType(APPLICATION_JSON)
                .with(userHttpBasic(USER))
                .content(writeValue(expected)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(ErrorType.VALIDATION_ERROR))
                .andDo(print());
    }

    @Test
    void testRegister() throws Exception {
        UserTo createdTo = new UserTo(null, "newName", "newlogin", "newPassword");

        ResultActions action = mockMvc.perform(post(REST_URL + "/register").contentType(APPLICATION_JSON)
                .content(writeValue(createdTo)))
                .andDo(print())
                .andExpect(status().isCreated());
        User returned = readFromJson(action, User.class);

        User created = createNewFromTo(createdTo);
        created.setId(returned.getId());

        assertMatch(returned, created);
        assertMatch(userService.getByLogin("newlogin"), created);
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void testDuplicateLogin() throws Exception {
        UserTo updatedTo = new UserTo(null, "newName", "login2", "newPassword");

        mockMvc.perform(put(REST_URL)
                .with(userHttpBasic(USER))
                .contentType(APPLICATION_JSON)
                .content(writeValue(updatedTo)))
                .andExpect(status().isConflict())
                .andExpect(errorType(ErrorType.VALIDATION_ERROR))
                .andExpect(jsonMessage("$.details", EXCEPTION_DUPLICATE_LOGIN))
                .andDo(print());
    }
}