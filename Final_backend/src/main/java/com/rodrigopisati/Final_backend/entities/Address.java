package com.rodrigopisati.Final_backend.entities;




import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;



@Getter
@Setter
@Entity
@Table(name = "Address")
public class Address {



    @Id
    @SequenceGenerator(name = "address_sequence" , sequenceName = "address_sequence" , allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator  ="address_sequence")
    @Column(name = "ID")
    private long id;


    @Column(name = "STREET" , nullable = false, length = 140)
    private String street;


    @Column(name = "NUMBER", nullable = false)
    private int number;


    @Column(name = "LOCALITY", nullable = false, length = 200)
    private String locality;


    @Column(name = "PROVINCE", nullable = false, length = 200)
    private String province;


    @OneToOne(mappedBy = "address")
    private Patient patient;

    public Patient getPatient() { return patient; }

    public void setPatient(Patient patient) { this.patient = patient;}

    public Address(){}

    public Address(long id, String street, int number, String locality, String province) {
        this.id = id;
        this.street = street;
        this.number = number;
        this.locality = locality;
        this.province = province;
    }

    public Address(String street, int number, String locality, String province) {
        this.street = street;
        this.number = number;
        this.locality = locality;
        this.province = province;
    }
}
