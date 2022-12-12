package com.rodrigopisati.Final_backend.services;


import com.rodrigopisati.Final_backend.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressService extends JpaRepository<Address, Long> {
}
