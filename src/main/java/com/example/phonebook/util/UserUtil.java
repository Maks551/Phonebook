package com.example.phonebook.util;

import com.example.phonebook.model.Role;
import com.example.phonebook.model.User;
import com.example.phonebook.to.UserTo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

public class UserUtil {

    public static UserTo asTo(User user) {
        return new UserTo(user.getId(), user.getName(), user.getLogin(), user.getPassword());
    }

    public static User updateFromTo(User user, UserTo userTo) {
        user.setName(userTo.getName());
        user.setLogin(userTo.getLogin());
        user.setPassword(userTo.getPassword());
        return user;
    }

    public static User createNewFromTo(UserTo newUser) {
        return new User(null, newUser.getLogin(), newUser.getPassword(), newUser.getName(),  Role.USER);
    }

    public static User prepareToSave(User user, PasswordEncoder passwordEncoder) {
        String password = user.getPassword();
        user.setPassword(StringUtils.isEmpty(password) ? password : passwordEncoder.encode(password));
        user.setLogin(user.getLogin().trim().toLowerCase());
        return user;
    }
}
