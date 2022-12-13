package com.rodrigopisati.Final_backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.rodrigopisati.Final_backend.entities.Address;
import com.rodrigopisati.Final_backend.entities.Dentist;
import com.rodrigopisati.Final_backend.entities.Patient;
import com.rodrigopisati.Final_backend.entities.Turn;
import com.rodrigopisati.Final_backend.services.imp.AddressServiceImp;
import com.rodrigopisati.Final_backend.services.imp.DentistServiceImp;
import com.rodrigopisati.Final_backend.services.imp.PatientServiceImp;
import com.rodrigopisati.Final_backend.services.imp.TurnServiceImp;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TurnIntegrationTest {

    @Autowired
    private TurnServiceImp turnServiceImp;
    @Autowired
    private DentistServiceImp dentistServiceImp;
    @Autowired
    private PatientServiceImp patientServiceImp;
    @Autowired
    private AddressServiceImp addressServiceImp;
    @Autowired
    private MockMvc mockMvc;


    public void loadDataInBd(){
        AddressServiceImp.saveAddress (new Address("callePaciente",3245, "localidadPaciente", "provinciaPaciente"));
        patientServiceImp.savePatient(new Patient("Paciente", "altoPaciente", "elmaildelpaciente@gmail.com", 34720654, LocalDate.of(2022, 12, 12), addressServiceImp.searchAddress(1L).get() ));
        DentistServiceImp.saveDentist(new Dentist("Juan","Dartes", 1230 ));
        turnServiceImp.saveTurn(new Turn(patientServiceImp.searchPatient(1L).get(), dentistServiceImp.searchDentist(1L).get(), LocalDate.of(2022,04,21)));
    }

    @Test
    @Order(0)
    public void testLoad() throws Exception{

        ObjectWriter writer = new ObjectMapper()
                .configure(SerializationFeature.WRAP_ROOT_VALUE,false)
                .writer();



        Address addressPatient = new Address("Calle01",789, "Moria", "Neusa");
        Patient patient = new Patient("Belfas","Piedra","belfas@mail.com",951753,addressPatient);
        Dentist dentist = new Dentist( "Balefor", "Legion", 85246);
        Address addressPatientLoad = new Address(1L,"Calle01",789, "Moria", "Neusa");
        Patient patientLoad = new Patient(1L,"Belfas","Piedra","belfas@mail.com",951753,addressPatientLoad);
        Dentist dentistLoad = new Dentist(1L, "Balefor", "Legion", 85246);

        String patientLoadJson = writer.writeValueAsString(patient);
        String dentistLoadJson = writer.writeValueAsString(dentist);

        MvcResult resultPatietn = mockMvc.perform(MockMvcRequestBuilders.post("/patient").contentType(MediaType.APPLICATION_JSON)
                        .content(patientLoadJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        MvcResult resultDentist = mockMvc.perform(MockMvcRequestBuilders.post("/dentist").contentType(MediaType.APPLICATION_JSON)
                        .content(dentistLoadJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Turn turn = new Turn(patientLoad,dentistLoad);
        String response = "{\"id\":1," +
                "\"paciente\":{\"id\":1,\"nombre\":\"Belfas\",\"apellido\":\"Piedra\",\"email\":\"belfas@mail.com\",\"dni\":951753,\"fechaIngreso\":null,\"" +
                "domicilio\":{\"id\":1,\"calle\":\"Calle01\",\"numero\":789,\"localidad\":\"Moria\",\"provincia\":\"Neusa\"}}," +
                "\"odontologo\":{\"id\":1,\"nombre\":\"Balefor\",\"apellido\":\"PLegion\",\"matricula\":85246}," +
                "\"fecha\":null}";


        String turnJson = writer.writeValueAsString(turn);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/turn")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(turnJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Assert.assertFalse(result.getResponse().getContentAsString().isEmpty());
        Assert.assertEquals(response, result.getResponse().getContentAsString());
    }

    @Test
    public void testlistar()throws Exception{
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/turn")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        Assert.assertFalse(result.getResponse().getContentAsString().isEmpty());
    }

    @Test
    public void testSearchForId()throws Exception{
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/turn/{id}","1").accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Assert.assertFalse(result.getResponse().getContentAsString().isEmpty());

    }

    @Test
    public void testUpdate() throws Exception{

        ObjectWriter writer = new ObjectMapper().configure(SerializationFeature.WRAP_ROOT_VALUE,false).writer();


        Dentist dentistUpdate = new Dentist( "Nebiros", "Behemota", 62485);
        Address addressPatientLoad = new Address(1L,"Calle01",789, "Moria", "Neusa");
        Patient patientLoad = new Patient(1L,"Belfas","Piedra","belfas@mail.com",951753,addressPatientLoad);
        Dentist dentistLoad = new Dentist(2L, "Nebiros", "Behemota", 62485);

        String dentistLoadJson = writer.writeValueAsString(dentistUpdate);


        MvcResult resultDentist = mockMvc.perform(MockMvcRequestBuilders.post("/dentist").contentType(MediaType.APPLICATION_JSON)
                        .content(dentistLoadJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Turn turn = new Turn(1L,patientLoad,dentistLoad);
        String response = "{\"id\":1," +
                "\"paciente\":{\"id\":1,\"nombre\":\"Belfas\",\"apellido\":\"Piedra\",\"email\":\"belfas@mail.com\",\"dni\":951753,\"fechaIngreso\":null,\"" +
                "domicilio\":{\"id\":1,\"calle\":\"Calle01\",\"numero\":789,\"localidad\":\"Moria\",\"provincia\":\"Neusa\"}}," +
                "\"odontologo\":{\"id\":2,\"nombre\":\"Nebiros\",\"apellido\":\"Behemota\",\"matricula\":62485}," +
                "\"fecha\":null}";

        String turnJson = writer.writeValueAsString(turn);


        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.put("/turn")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(turnJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Assert.assertFalse(result.getResponse().getContentAsString().isEmpty());
        Assert.assertEquals(response, result.getResponse().getContentAsString());
        System.out.println(result.getResponse().getContentAsString());
    }
    @Test
    public void testDelete() throws Exception{

        String response = "Turno eliminado";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/turn/{id}","1").accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Assert.assertEquals(response, result.getResponse().getContentAsString());
    }

}

