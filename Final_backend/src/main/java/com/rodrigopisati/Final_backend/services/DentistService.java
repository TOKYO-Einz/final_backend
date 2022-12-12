package com.rodrigopisati.Final_backend.services;


import com.rodrigopisati.Final_backend.entities.Dentist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DentistService extends JpaRepository<Dentist, Long> {
}
