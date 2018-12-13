package com.example.phonebook.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends AbstractBaseEntity {
    @NotBlank(message = "Login is mandatory")
    @Size(min = 3)
    @Column(name = "login", nullable = false)
    private String login;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 5)
    private String password;

    @NotBlank(message = "Name is mandatory")
    @Size(min = 5)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Phonebook> phonebooks;

    public User(User user) {
        this(user.getId(), user.login, user.password, user.name);
    }

    public User(Integer id, String login, String password, String name) {
        super(id);
        this.login = login;
        this.password = password;
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login=" + login +
                ", name=" + name +
                '}';
    }
}
