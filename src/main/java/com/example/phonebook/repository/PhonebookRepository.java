package com.example.phonebook.repository;

import com.example.phonebook.model.Phonebook;

import java.util.List;

public interface PhonebookRepository {
    Phonebook save(Phonebook pbEntry, int userId);

    boolean delete(int id, int userId);

    Phonebook get(int id, int userId);

    List<Phonebook> getAll(int userId);
}
