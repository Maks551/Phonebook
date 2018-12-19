package com.example.phonebook.repository.datajpa;

import com.example.phonebook.model.PhonebookEntry;
import com.example.phonebook.repository.PhonebookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public PhonebookEntry save(PhonebookEntry pbEntry, int userId) {
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
    public PhonebookEntry get(int id, int userId) {
        return phonebookRepo.findById(id)
                .filter(pbEntry -> pbEntry.getUser().getId() == userId)
                .orElse(null);
    }

    @Override
    public List<PhonebookEntry> getAll(int userId) {
        return phonebookRepo.getAll(userId);
    }
}
