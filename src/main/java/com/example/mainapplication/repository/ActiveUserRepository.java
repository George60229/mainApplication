package com.example.mainapplication.repository;

import com.example.mainapplication.model.ActiveUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActiveUserRepository extends JpaRepository<ActiveUser,Integer> {
    List<ActiveUser> findByUsername(String username);
}
