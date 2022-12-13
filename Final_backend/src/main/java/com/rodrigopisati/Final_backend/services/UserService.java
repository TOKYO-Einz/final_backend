package com.rodrigopisati.Final_backend.services;


import com.rodrigopisati.Final_backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserService extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
