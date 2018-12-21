package com.example.phonebook.repository.datajpa;

import com.example.phonebook.model.Phonebook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface CrudPhoneBookRepository extends JpaRepository<Phonebook, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Phonebook p WHERE p.id=:id AND p.user.id=:userId")
    int delete(@Param("id") int id, @Param("userId") int userId);

    @Override
    @Transactional
    Phonebook save(Phonebook item);

    @Query("SELECT p FROM Phonebook p WHERE p.user.id=:userId ORDER BY p.lastName")
    List<Phonebook> getAll(@Param("userId") int userId);
}
