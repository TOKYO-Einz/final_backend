package com.rodrigopisati.Final_backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.rodrigopisati.Final_backend.entities.Address;
import com.rodrigopisati.Final_backend.entities.Patient;
import com.rodrigopisati.Final_backend.services.imp.AddressServiceImp;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
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

@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PatientIntegrationTest {

    @Autowired
    private AddressServiceImp addressServiceImp;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testLoad() throws Exception{
        Address address = new Address("pelegrini",549, "Rafaela", "Santa Fe");
        Patient patient = new Patient("Dartanian", "Dark", "dartanian@mail.com", 1000001,address );
        String response = "{\"id\":1,\"name\":\"Dartanian\",\"lastname\":\"Dark\",\"email\":\"dartanian@mail.com\",\"dni\":1000001,\"date of admission\":null,\"address\":{\"id\":1,\"street\":\"calleA\",\"number\":123,\"locality\":\"Hell\",\"province\":\"Inframundo\"},\"turn\":[]}";



        ObjectWriter writer = new ObjectMapper()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .configure(SerializationFeature.WRAP_ROOT_VALUE,false)
                .writer();

        String patientJson = writer.writeValueAsString(patient);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/patient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patientJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Assert.assertFalse(result.getResponse().getContentAsString().isEmpty());
        Assert.assertEquals(response, result.getResponse().getContentAsString());
    }

    @Test
    public void testList() throws Exception{
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/patient").accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Assert.assertFalse((result.getResponse().getContentAsString().isEmpty()));
    }

    @Test
    public void testSearchForId()throws Exception{
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/patient/{id}","1").accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Assert.assertFalse(result.getResponse().getContentAsString().isEmpty());

    }
    @Test
    public void testSearchForEmail()throws Exception{
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/patient/email?email={email}","pasiente1@muypaciente.com").accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Assert.assertFalse(result.getResponse().getContentAsString().isEmpty());

    }

    @Test
    public void testUpdate() throws Exception{
        Address address = addressServiceImp.updateAddress( new Address(1L,"calleB", 456,"Tartaro", "Mordor" ));
        Patient patient = new Patient(1L,"Pruslas", "Grey", "pruslas@mail.com", 2000002,address);
        String response = "{\"id\":1,\"nombre\":\"Pruslas\",\"apellido\":\"Grey\",\"email\":\"pruslas@mail.com\",\"dni\":2000002,\"fechaIngreso\":null,\"domicilio\":{\"id\":1,\"calle\":\"calleB\",\"numero\":456,\"localidad\":\"Tartaro\",\"provincia\":\"Mordor\"},\"turnos\":[]}";


        ObjectWriter writer = new ObjectMapper().configure(SerializationFeature.WRAP_ROOT_VALUE,false).writer();
        String patientJson = writer.writeValueAsString(patient);

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.put("/patient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patientJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Assert.assertFalse(result.getResponse().getContentAsString().isEmpty());
        Assert.assertEquals(response, result.getResponse().getContentAsString());
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testBorrar() throws Exception{

        String response = "Paciente eliminado";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/pacientes/{id}","1").accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Assert.assertEquals(response, result.getResponse().getContentAsString());
    }


}
