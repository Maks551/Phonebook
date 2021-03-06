package com.example.phonebook.service;

import com.example.phonebook.model.Phonebook;
import com.example.phonebook.repository.PhonebookRepository;
import com.example.phonebook.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static com.example.phonebook.util.ValidationUtil.checkNotFoundWithId;

@Service
public class PhonebookServiceImpl implements PhonebookService {
    private final String PHONEBOOK_CACHE = "phonebooks";
    private final PhonebookRepository phonebookRepo;

    @Autowired
    public PhonebookServiceImpl(PhonebookRepository phonebookRepo) {
        this.phonebookRepo = phonebookRepo;
    }

    @Override
    public Phonebook get(int id, int userId) {
        return checkNotFoundWithId(phonebookRepo.get(id, userId), id);
    }

    @CacheEvict(value = PHONEBOOK_CACHE, key = "#userId")
    @Override
    public void delete(int id, int userId) throws NotFoundException {
        checkNotFoundWithId(phonebookRepo.delete(id, userId), id);
    }

    @CacheEvict(value = PHONEBOOK_CACHE, key = "#userId")
    @Override
    public Phonebook create(Phonebook pbEntry, int userId) {
        Assert.notNull(pbEntry, "Phonebook must not be null");
        return phonebookRepo.save(pbEntry, userId);
    }

    @CacheEvict(value = PHONEBOOK_CACHE, key = "#userId")
    @Override
    public void update(Phonebook pbEntry, int userId) throws NotFoundException {
        checkNotFoundWithId(phonebookRepo.save(pbEntry, userId), pbEntry.getId());
    }

    @Override
    @Cacheable(PHONEBOOK_CACHE)
    public List<Phonebook> getAll(int userId) {
        return phonebookRepo.getAll(userId);
    }
}
