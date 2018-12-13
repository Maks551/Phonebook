package com.example.phonebook.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "login", name = "users_unique_login_idx")})
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

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Phonebook> phonebooks;

    public User(User user) {
        this(user.getId(), user.login, user.password, user.name, user.getRoles());
    }

    public User(Integer id, String login, String password, String name, Role role, Role... roles) {
        this(id, login, password, name, EnumSet.of(role, roles));
    }

    public User(Integer id, String login, String password, String name, Collection<Role> roles) {
        super(id);
        this.login = login;
        this.password = password;
        this.name = name;
        setRoles(roles);
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = CollectionUtils.isEmpty(roles) ? Collections.emptySet() : EnumSet.copyOf(roles);
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
