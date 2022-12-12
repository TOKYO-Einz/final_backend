package com.rodrigopisati.Final_backend.services.imp;


import com.rodrigopisati.Final_backend.entities.Turn;
import com.rodrigopisati.Final_backend.exceptions.BadRequestException;
import com.rodrigopisati.Final_backend.exceptions.ResourceNotFoundException;
import com.rodrigopisati.Final_backend.services.TurnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TurnServiceImp {


    @Autowired
    private TurnService repository;


    public List<Turn> listTurns() { return repository.findAll();}


    public Optional<Turn> searchTurn(Long id) { return repository.findById(id); }


    public Turn saveTurn(Turn turn) { return (Turn) repository.save(turn);}


    public Turn updateTurn(Turn turn) throws BadRequestException {
        if (searchTurn(turn.getId()).isPresent()) {
            return (Turn) repository.save(turn);
        }else{
            throw new BadRequestException("No se encontro turno, por lo tanto no se actualizo");
        }
    }


    public void deleteTurn(Long id) throws ResourceNotFoundException {
        Optional<Turn> turn = searchTurn(id);
        if (turn.isPresent()) {
            repository.deleteById(id);
        }else {
            throw new ResourceNotFoundException("Turno con id: " + id + "no se pudo eliminar, no existe");
        }
    }
}
