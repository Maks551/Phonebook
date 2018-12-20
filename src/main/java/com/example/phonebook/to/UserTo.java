package com.example.phonebook.to;

import com.example.phonebook.HasId;
import org.hibernate.validator.constraints.SafeHtml;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

import static com.example.phonebook.util.ValidationUtil.LOGIN_PATTERN;

public class UserTo implements Serializable, HasId {

    public Integer id;

    @NotBlank
    @Size(min = 3)
    @SafeHtml
    private String name;

    @NotBlank
    @Pattern(regexp = LOGIN_PATTERN, message = "Invalid login!")
    @Size(min = 3)
    @SafeHtml // https://stackoverflow.com/questions/17480809
    private String login;

    @Size(min = 5)
    private String password;

    public UserTo() {
    }

    public UserTo(Integer id, String name, String login, String password) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login.trim().toLowerCase();
    }

    @Override
    public String toString() {
        return "UserTo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", login='" + login + '\'' +
                '}';
    }
}
