package com.rodrigopisati.Final_backend.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "Turns")
public class Turn {


    @Id
    @SequenceGenerator(name = "turn_sequence", sequenceName = "turn_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "turn_sequence")
    private long id;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    @JsonIgnoreProperties("hibernateLazyInitializer"/*, "handler */)
    private Patient patient;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "dentist_id")
    @JsonIgnoreProperties("hibernateLazyInitializer"/*, "handler */)
    private Dentist dentist;


    @Column(name = "TURN_DATE", nullable = false, length = 200)
    private LocalDate dateTurn;


    public Turn(long id, Patient patient, Dentist dentist) {
        this.id = id;
        this.patient = patient;
        this.dentist = dentist;
    }


    public Turn(Patient patient, Dentist dentist) {
        this.patient = patient;
        this.dentist = dentist;
    }


    public Turn(Patient patient, Dentist dentist, LocalDate dateTurn) {
        this.patient = patient;
        this.dentist = dentist;
        this.dateTurn = dateTurn;
    }
}
