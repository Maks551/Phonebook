package com.example.phonebook.repository;

import com.example.phonebook.model.PhonebookEntry;

import java.util.List;

public interface PhonebookRepository {
    PhonebookEntry save(PhonebookEntry pbEntry, int userId);

    boolean delete(int id, int userId);

    PhonebookEntry get(int id, int userId);

    List<PhonebookEntry> getAll(int userId);
}
