package com.rodrigopisati.Final_backend.services.imp;


import com.rodrigopisati.Final_backend.entities.Dentist;
import com.rodrigopisati.Final_backend.entities.Patient;
import com.rodrigopisati.Final_backend.exceptions.BadRequestException;
import com.rodrigopisati.Final_backend.exceptions.ResourceNotFoundException;
import com.rodrigopisati.Final_backend.services.DentistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class DentistServiceImp {


    @Autowired
    private DentistService repository;


    public List<Dentist> listDentist() { return repository.findAll();}


    public Optional<Dentist> searchDentist(Long id) {return repository.findById(id);}


    public  Dentist saveDentist(Dentist dentist) { return repository.save(dentist);}


    public Dentist updateDentist(Dentist dentist) throws BadRequestException {
        if (searchDentist(dentist.getId()).isPresent()) {
            return (Dentist) repository.save(dentist);
        }else{
            throw new BadRequestException("No se encontro el dentista a actualizar");
        }
    }


    public void deleteDentist(Long id) throws ResourceNotFoundException {
        Optional<Dentist> dentist = searchDentist(id);
        if(dentist.isPresent()) {
            repository.deleteById(id);
        }else{
            throw new ResourceNotFoundException("No se pudo eliminar, debido a que no existe ningun Dentista con ID: " + id + ".");
        }
    }
}
