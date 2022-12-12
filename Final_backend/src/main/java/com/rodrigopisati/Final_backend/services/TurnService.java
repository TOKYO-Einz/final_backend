package com.rodrigopisati.Final_backend.services;


import com.rodrigopisati.Final_backend.entities.Turn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TurnService extends JpaRepository<Turn, Long> {
}
