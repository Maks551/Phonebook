package com.example.phonebook.repository.datajpa;

import com.example.phonebook.model.Phonebook;
import com.example.phonebook.repository.PhonebookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Profile("mysql")
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
    @Transactional
    public Phonebook save(Phonebook pbEntry, int userId) {
        if (!pbEntry.isNew() && get(pbEntry.getId(), userId) == null) {
            return null;
        }
        pbEntry.setUser(userRepo.getOne(userId));
        return phonebookRepo.save(pbEntry);
    }

    @Override
    public boolean delete(int id, int userId) {
        return phonebookRepo.delete(id, userId) != 0;
    }

    @Override
    public Phonebook get(int id, int userId) {
        return phonebookRepo.findById(id)
                .filter(pbEntry -> pbEntry.getUser().getId() == userId)
                .orElse(null);
    }

    @Override
    public List<Phonebook> getAll(int userId) {
        return phonebookRepo.getAll(userId);
    }
}
