package com.rodrigopisati.Final_backend.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "Dentists")

public class Dentist {


    @Id
    @SequenceGenerator(name = "dentist_sequence", sequenceName = "dentist_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dentist_Sequence")
    private long id;


    @Column(name = "NAME", nullable = false, length = 200)
    private String name;


    @Column(name = "LASTNAME", nullable = false, length = 200)
    private String lastname;


    @Column(name = "REGISTRATION", nullable = false, length = 120)
    private int registration;


    @OneToMany(mappedBy = "dentist", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Turn> turns =new HashSet<>();


    public Dentist() {
    }


    public Dentist(Long id, String name, String lastname, int registration) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.registration = registration;
    }


    public Dentist(String name, String lastname, int registration) {
        this.name = name;
        this.lastname = lastname;
        this.registration = registration;
    }


    
}
