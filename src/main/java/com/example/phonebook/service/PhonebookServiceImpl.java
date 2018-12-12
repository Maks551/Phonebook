package com.example.phonebook.service;

import com.example.phonebook.model.Phonebook;
import com.example.phonebook.repository.PhonebookRepository;
import com.example.phonebook.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static com.example.phonebook.util.ValidationUtil.checkNotFoundWithId;

@Service
public class PhonebookServiceImpl implements PhonebookService {
    private final PhonebookRepository phonebookRepo;

    @Autowired
    public PhonebookServiceImpl(PhonebookRepository phonebookRepo) {
        this.phonebookRepo = phonebookRepo;
    }

    @Override
    public Phonebook get(int id, int userId) {
        return checkNotFoundWithId(phonebookRepo.get(id, userId), id);
    }

    @Override
    public void delete(int id, int userId) throws NotFoundException {
        checkNotFoundWithId(phonebookRepo.delete(id, userId), id);
    }

    @Override
    public Phonebook create(Phonebook phonebook, int userId) {
        Assert.notNull(phonebook, "phonebook must not be null");
        return phonebookRepo.save(phonebook, userId);
    }

    @Override
    public void update(Phonebook phonebook, int userId) throws NotFoundException {
        checkNotFoundWithId(phonebookRepo.save(phonebook, userId), phonebook.getId());
    }

    @Override
    public List<Phonebook> getAll(int userId) {
        return phonebookRepo.getAll(userId);
    }
}
