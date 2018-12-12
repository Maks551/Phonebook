package com.example.phonebook.repository.datajpa;

import com.example.phonebook.model.Phonebook;
import com.example.phonebook.repository.PhonebookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PhonebookRepositoryImpl implements PhonebookRepository {
    private final CrudPhoneBookRepository phonebookRepo;
    private final CrudUserRepository userRepo;

    @Autowired
    public PhonebookRepositoryImpl(CrudPhoneBookRepository phonebookRepo, CrudUserRepository userRepo) {
        this.phonebookRepo = phonebookRepo;
        this.userRepo = userRepo;
    }

    @Override
    public Phonebook save(Phonebook phoneBook, int userId) {
        if (!phoneBook.isNew() && get(phoneBook.getId(), userId) == null) {
            return null;
        }
        phoneBook.setUser(userRepo.getOne(userId));
        return phonebookRepo.save(phoneBook);
    }

    @Override
    public boolean delete(int id, int userId) {
        return phonebookRepo.delete(id, userId) != 0;
    }

    @Override
    public Phonebook get(int id, int userId) {
        return phonebookRepo.findById(id)
                .filter(phoneBook -> phoneBook.getUser().getId() == userId)
                .orElse(null);
    }

    @Override
    public List<Phonebook> getAll(int userId) {
        return phonebookRepo.getAll(userId);
    }
}