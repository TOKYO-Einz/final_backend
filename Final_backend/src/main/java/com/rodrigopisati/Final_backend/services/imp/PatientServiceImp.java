package com.rodrigopisati.Final_backend.services.imp;


import com.rodrigopisati.Final_backend.entities.Patient;
import com.rodrigopisati.Final_backend.exceptions.BadRequestException;
import com.rodrigopisati.Final_backend.exceptions.ResourceNotFoundException;
import com.rodrigopisati.Final_backend.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceImp {


    @Autowired
    private PatientService repository;


    public List<Patient> listPatient() { return repository.findAll();}


    public Optional<Patient> searchPatient(Long id) {
        Optional<Patient> patient= repository.findById(id);
        return repository.findById(id);
    }


    public Patient savePatient(Patient patient) { return repository.save(patient);}


    public Patient updatePatient(Patient patient) throws BadRequestException{
        if (searchPatient(patient.getId()).isPresent()) {
            return (Patient) repository.save(patient);
        }else{
            throw new BadRequestException("No se pudo actualizar el paciente ya que no se encontro");
        }
    }


    public void deletePatient (Long id) throws ResourceNotFoundException {
        Optional<Patient> patient = searchPatient(id);
        if (patient.isPresent()) {
            repository.deleteById(id);
        }else{
            throw new ResourceNotFoundException("No se pudo eliminar el paciente con ID: " + id + " , este paciente no existe.");
        }
    }
}
