package com.example.phonebook.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.example.phonebook.util.ValidationUtil.HOME_PHONE_NUMBER_PATTERN;
import static com.example.phonebook.util.ValidationUtil.MOBILE_PHONE_NUMBER_PATTERN;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "phone_book", uniqueConstraints = {@UniqueConstraint(columnNames = {"mobile_phone_number", "user_id"}, name = "phone_books_mobile_phone_number_idx")})
public class PhonebookEntry extends AbstractBaseEntity {
    @NotBlank(message = "Last Name is mandatory")
    @Size(min = 4)
    private String lastName;

    @NotBlank(message = "First Name is mandatory")
    @Size(min = 4)
    private String firstName;

    @NotBlank(message = "Surname is mandatory")
    @Size(min = 4)
    private String surname;

    @Pattern(regexp = MOBILE_PHONE_NUMBER_PATTERN, message = "Not valid phone number")
    @NotBlank(message = "Mobile Phone Number is mandatory")
    @Column(name = "mobile_phone_number")
    private String mobilePhoneNumber;

    @Pattern(regexp = HOME_PHONE_NUMBER_PATTERN, message = "Not valid phone number")
    private String homePhoneNumber;
    private String address;

    @Email(message = "Email is invalid")
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User user;

    public PhonebookEntry(PhonebookEntry pbEntry) {
        this(pbEntry.id, pbEntry.lastName, pbEntry.firstName,
                pbEntry.surname, pbEntry.mobilePhoneNumber,
                pbEntry.homePhoneNumber, pbEntry.address, pbEntry.email);
    }

    public PhonebookEntry(Integer id, String lastName, String firstName, String surname, String mobilePhoneNumber,
                          String homePhoneNumber, String address, String email) {
        super(id);
        this.lastName = lastName;
        this.firstName = firstName;
        this.surname = surname;
        this.mobilePhoneNumber = mobilePhoneNumber;
        this.homePhoneNumber = homePhoneNumber;
        this.address = address;
        this.email = email;
    }

    @Override
    public String toString() {
        return "PhonebookEntry{" +
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
