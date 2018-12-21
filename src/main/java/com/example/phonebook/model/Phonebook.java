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
@Table(name = "phone_book", uniqueConstraints = {@UniqueConstraint(columnNames = {"mobile_phone_number", "user_id"}, name = "phone_book_mobile_phone_number_idx")})
public class Phonebook extends AbstractBaseEntity {
    @Column(name = "last_name")
    @NotBlank
    @Size(min = 4)
    private String lastName;

    @NotBlank
    @Size(min = 4)
    private String firstName;

    @NotBlank
    @Size(min = 4)
    private String surname;

    @Pattern(regexp = MOBILE_PHONE_NUMBER_PATTERN)
    @NotBlank
    @Column(name = "mobile_phone_number")
    private String mobilePhoneNumber;

//    @Column(name = "home_phone_number", columnDefinition = "varchar(255) default ''")
//    @Column(name = "home_phone_number", columnDefinition = "varchar() default 1")
    @Pattern(regexp = HOME_PHONE_NUMBER_PATTERN)
    private String homePhoneNumber;
    private String address;

    @Email
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User user;

    public Phonebook(Phonebook pbEntry) {
        this(pbEntry.id, pbEntry.lastName, pbEntry.firstName,
                pbEntry.surname, pbEntry.mobilePhoneNumber,
                pbEntry.homePhoneNumber, pbEntry.address, pbEntry.email);
    }

    public Phonebook(Integer id, String lastName, String firstName, String surname, String mobilePhoneNumber,
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

    public void setHomePhoneNumber(String homePhoneNumber) {
        this.homePhoneNumber = homePhoneNumber == null ? "" : homePhoneNumber;
    }

    @Override
    public String toString() {
        return "Phonebook{" +
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
