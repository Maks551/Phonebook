package com.example.phonebook;


import com.example.phonebook.model.User;
import com.example.phonebook.to.UserTo;
import com.example.phonebook.util.UserUtil;

public class AuthorizedUser extends org.springframework.security.core.userdetails.User {
//    private static final long serialVersionUID = 1;

    private UserTo userTo;

    public AuthorizedUser(User user) {
        super(user.getLogin(), user.getPassword(), user.isEnabled(), true, true, true, user.getRoles());
        this.userTo = UserUtil.asTo(user);
    }

    public int getId() {
        return userTo.getId();
    }

    public void update(UserTo newTo) {
        userTo = newTo;
    }

    public UserTo getUserTo() {
        return userTo;
    }

    @Override
    public String toString() {
        return userTo.toString();
    }
}