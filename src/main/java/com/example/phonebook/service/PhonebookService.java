package com.example.phonebook.service;

import com.example.phonebook.model.Phonebook;
import com.example.phonebook.util.exception.NotFoundException;

import java.util.List;

public interface PhonebookService {
    Phonebook get(int id, int userId);

    void delete(int id, int userId) throws NotFoundException;

    Phonebook create(Phonebook phonebook, int userId);

    void update(Phonebook phonebook, int userId) throws NotFoundException;

    List<Phonebook> getAll(int userId);
}
