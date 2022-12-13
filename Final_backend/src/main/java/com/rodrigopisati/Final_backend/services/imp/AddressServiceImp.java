package com.rodrigopisati.Final_backend.services.imp;


import com.rodrigopisati.Final_backend.entities.Address;
import com.rodrigopisati.Final_backend.exceptions.BadRequestException;
import com.rodrigopisati.Final_backend.exceptions.ResourceNotFoundException;
import com.rodrigopisati.Final_backend.services.AddressService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImp {


    @Autowired
    private AddressService repository;


    public Optional<Address> searchAddress(Long id) { return repository.findById(id);}


    public List<Address> listAddress(Address address) { return repository.findAll();}


    public  Address saveAddress(Address address) { return (Address) repository.save(address);}


    public void deleteAddress( Long id) throws ResourceNotFoundException {
        Optional<Address> address = searchAddress(id);
        if (address.isPresent()) {
            repository.deleteById(id);
        }else{
            throw new ResourceNotFoundException("Domicilio con id: " + id + " no se pudo eliminar debido a que no existe");
        }
    }


    public Address updateAddress(Address address) throws BadRequestException {
        if (searchAddress(address.getId()).isPresent()) {
            return (Address) repository.save(address);
        }else {
            throw new BadRequestException("No se pudo encontrar el domicilio, por lo tanto no fue actualizado");
        }

    }



}
