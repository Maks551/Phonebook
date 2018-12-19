package com.example.phonebook.repository.datajpa;

import com.example.phonebook.model.PhonebookEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface CrudPhoneBookRepository extends JpaRepository<PhonebookEntry, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM PhonebookEntry p WHERE p.id=:id AND p.user.id=:userId")
    int delete(@Param("id") int id, @Param("userId") int userId);

    @Override
    @Transactional
    PhonebookEntry save(PhonebookEntry item);

    @Query("SELECT p FROM PhonebookEntry p WHERE p.user.id=:userId ORDER BY p.lastName")
    List<PhonebookEntry> getAll(@Param("userId") int userId);
}
