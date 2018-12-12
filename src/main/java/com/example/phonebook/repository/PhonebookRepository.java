package com.example.phonebook.repository;

import com.example.phonebook.model.Phonebook;

import java.util.List;

public interface PhonebookRepository {
    Phonebook save(Phonebook phoneBook, int userId);

    boolean delete(int id, int userId);

    Phonebook get(int id, int userId);

    // ORDERED dateTime desc
    List<Phonebook> getAll(int userId);

//    List<PhoneBook> getBetweenName(String name, int userId);
}
