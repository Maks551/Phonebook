package com.example.phonebook.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends AbstractBaseEntity {
    @NotBlank(message = "Login is mandatory")
    @Min(3)
    @Column(name = "login", nullable = false)
    private String login;

    @NotBlank(message = "Password is mandatory")
    @Min(5)
    private String password;

    @NotBlank(message = "Name is mandatory")
    @Min(5)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Phonebook> phoneBooks;

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
