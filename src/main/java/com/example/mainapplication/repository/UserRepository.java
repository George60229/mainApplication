package com.example.mainapplication.repository;

import com.example.mainapplication.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<MyUser, Integer> {
    @Transactional
    @Modifying
    @Query("update MyUser m set m.isBlocked = ?2 where m.username = ?1")
    void updateIsBlockedByUsername(String username,boolean blocked);
    MyUser findByEmail(String email);

    MyUser findByUsername(String login);



}
