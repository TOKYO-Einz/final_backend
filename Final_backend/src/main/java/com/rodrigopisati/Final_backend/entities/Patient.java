package com.rodrigopisati.Final_backend.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "Patients")
public class Patient {


    @Id
    @SequenceGenerator(name = "patient_sequence", sequenceName = "patient_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "patient_sequence")
    @Column(name = "ID")
    private long id;


    @Column(name = "NAME", nullable = false, length = 200)
    private String name;


    @Column(name = "LASTNAME", nullable = false, length = 200)
    private String lastname;


    @Column(name = "EMAIL", nullable = false, length = 220)
    private String email;


    @Column(name = "DNI", nullable = false)
    private int dni;


    @Column(name = "DATE_OF_ADMISSION")
    private LocalDate dateOfAdmission;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "address_Id", referencedColumnName = "id")
    private Address address;


    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Turn> turns = new HashSet<>();


    public Patient() {
    }

    public Patient(long id, String name, String lastname, String email, int dni, Address address) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.dni = dni;
        this.address = address;
    }

    public Patient(String name, String lastname, String email, int dni, LocalDate dateOfAdmission, Address address) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.dni = dni;
        this.dateOfAdmission = dateOfAdmission;
        this.address = address;
    }


    public Patient(String name, String lastname, String email, int dni, Address address) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.dni = dni;
        this.address = address;
    }
}
