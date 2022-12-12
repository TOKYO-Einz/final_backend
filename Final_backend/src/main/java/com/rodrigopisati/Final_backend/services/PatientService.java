package com.rodrigopisati.Final_backend.services;


import com.rodrigopisati.Final_backend.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientService extends JpaRepository<Patient, Long> {


    @Query("Select p From Patient p where p.email=?1")
    Optional<Patient> searchPatientForEmail(String email);
}
