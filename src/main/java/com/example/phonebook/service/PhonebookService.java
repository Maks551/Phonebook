package com.example.phonebook.service;

import com.example.phonebook.model.PhonebookEntry;
import com.example.phonebook.util.exception.NotFoundException;

import java.util.List;

public interface PhonebookService {
    PhonebookEntry get(int id, int userId);

    void delete(int id, int userId) throws NotFoundException;

    PhonebookEntry create(PhonebookEntry pbEntry, int userId);

    void update(PhonebookEntry pbEntry, int userId) throws NotFoundException;

    List<PhonebookEntry> getAll(int userId);
}
