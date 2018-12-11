package com.example.phonebook.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "phone_book")
public class Phonebook extends AbstractBaseEntity {
    @NotBlank(message = "Last Name is mandatory")
    @Min(4)
    private String lastName;

    @NotBlank(message = "First Name is mandatory")
    @Min(4)
    private String firstName;

    @NotBlank(message = "Surname is mandatory")
    @Min(4)
    private String surname;

    @NotBlank(message = "Mobile Phone Number is mandatory")
    private String mobilePhoneNumber;

    private String homePhoneNumber;
    private String address;

    @Email
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Override
    public String toString() {
        return "PhoneBook{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", surname='" + surname + '\'' +
                ", mobilePhoneNumber='" + mobilePhoneNumber + '\'' +
                ", homePhoneNumber='" + homePhoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
