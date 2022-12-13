package com.rodrigopisati.Final_backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.rodrigopisati.Final_backend.entities.Dentist;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
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
public class DentistIntegrationTest {


    @Autowired
    private MockMvc mockMvc;


    @Test
    @Order(1)
    public void testCargar() throws Exception{
        Dentist dentist = new Dentist("Rodrigo", "Pisati", 1040);
        String response = "{\"id\":1,\"name\":\"Rodrigo\",\"lastname\":\"Pisati\",\"registration\":1040,\"turn\":[]}";

        ObjectWriter writer = new ObjectMapper()
                .configure(SerializationFeature.WRAP_ROOT_VALUE,false)
                .writer();

        String DentistJson = writer.writeValueAsString(dentist);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/dentist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(DentistJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Assert.assertFalse(result.getResponse().getContentAsString().isEmpty());
        Assert.assertEquals(response, result.getResponse().getContentAsString());
    }

    @Test
    @Order(2)
    public void testList() throws Exception{
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/dentist").accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Assert.assertFalse((result.getResponse().getContentAsString().isEmpty()));
    }

    @Test
    @Order(3)
    public void testSearchForId()throws Exception{
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/dentist/{id}","1").accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Assert.assertFalse(result.getResponse().getContentAsString().isEmpty());

    }

    @Test
    @Order(4)
    public void testActualizar() throws Exception{
        Dentist dentist = new Dentist(1L,"Anabel","Marcial",1001);
        String response = "{\"id\":1,\"name\":\"Anabel\",\"lastname\":\"Black\",\"registration\":1001,\"turnos\":[]}";

        ObjectWriter writer = new ObjectMapper().configure(SerializationFeature.WRAP_ROOT_VALUE,false).writer();
        String dentistJson = writer.writeValueAsString(dentist);

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.put("/dentist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dentistJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Assert.assertFalse(result.getResponse().getContentAsString().isEmpty());
        Assert.assertEquals(response, result.getResponse().getContentAsString());
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @Order(5)
    public void testDelete() throws Exception{

        String response = "Dentista Eliminado";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/dentist/{id}","1").accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Assert.assertEquals(response, result.getResponse().getContentAsString());
    }
}
