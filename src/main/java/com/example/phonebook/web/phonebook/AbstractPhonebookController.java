package com.example.phonebook.web.phonebook;

import com.example.phonebook.model.PhonebookEntry;
import com.example.phonebook.service.PhonebookService;
import com.example.phonebook.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.phonebook.util.ValidationUtil.assureIdConsistent;
import static com.example.phonebook.util.ValidationUtil.checkNew;
import static com.example.phonebook.web.SecurityUtil.authUserId;

public abstract class AbstractPhonebookController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    protected PhonebookService service;
    @Autowired
    protected UserService userService;

    protected PhonebookEntry get(int id) {
        int userId = authUserId();
        log.info("get phonebook entry {} for user {}", id, userId);
        return service.get(id, userId);
    }

    protected List<PhonebookEntry> getAll() {
        int userId = authUserId();
        log.info("get phonebook for user {}", userId);
        return service.getAll(userId);
    }

    protected void delete(int id) {
        int userId = authUserId();
        log.info("delete phonebook entry {} for user {}", id, userId);
        service.delete(id, userId);
    }

    protected void update(PhonebookEntry pbEntry, int id) {
        int userId = authUserId();
        assureIdConsistent(pbEntry, id);
        log.info("update {} for user {}", pbEntry, userId);
        service.update(pbEntry, userId);
    }

    protected PhonebookEntry create(PhonebookEntry pbEntry) {
        int userId = authUserId();
        log.info("create {} for user {}", pbEntry, userId);
        checkNew(pbEntry);
        return service.create(pbEntry, userId);
    }

    protected List<PhonebookEntry> getFilter(String lastName, String firstName, String mobilePhoneNumber, String homePhoneNumber) {
        int userId = authUserId();
        log.info("filter lastName {}, firstName - {}, mobilePhoneNumber - {}, homePhoneNumber - {} for user {}", lastName, firstName, mobilePhoneNumber, homePhoneNumber, userId);

        return getAll().stream()
                .filter((p) -> {
                    if (lastName!=null && !lastName.trim().isEmpty()) {
                        return p.getLastName().toLowerCase().contains(lastName.trim().toLowerCase());
                    }
                    return true;
                })
                .filter((p) -> {
                    if (firstName!=null && !firstName.trim().isEmpty()) {
                        return p.getFirstName().toLowerCase().contains(firstName.trim().toLowerCase());
                    }
                    return true;
                }).filter((p) -> {
                    if (mobilePhoneNumber!=null && !mobilePhoneNumber.trim().isEmpty()) {
                        return p.getMobilePhoneNumber().toLowerCase().contains(mobilePhoneNumber.trim().toLowerCase());
                    }
                    return true;
                })
                .filter((p) -> {
                    if (homePhoneNumber!=null && !homePhoneNumber.trim().isEmpty()) {
                        return p.getHomePhoneNumber().toLowerCase().contains(homePhoneNumber.trim().toLowerCase());
                    }
                    return true;
                }).collect(Collectors.toList());
    }
}