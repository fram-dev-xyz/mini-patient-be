package com.xtramile.fram.controller;

import com.xtramile.fram.model.dto.PatientDto;
import com.xtramile.fram.service.PatientService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = PatientController.class)
class PatientControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    PatientService patientService;

    @Test
    void getPatient_shouldOk() throws Exception {
        PatientDto patientDto = PatientDto.builder()
                .id(1L)
                .patientId("dummnyPatientId1")
                .firstName("Fram")
                .build();
        Mockito.when(patientService.getPatient(1L)).thenReturn(patientDto);
        mockMvc.perform(MockMvcRequestBuilders.get("/patients/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath(".id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath(".patientId").value("dummnyPatientId1"))
                .andExpect(MockMvcResultMatchers.jsonPath(".firstName").value("Fram"));

    }

    @Test
    void getPatient_should404NotFound() throws Exception {
        Mockito.when(patientService.getPatient(1L)).thenThrow(new EntityNotFoundException("Patient not found"));
        mockMvc.perform(MockMvcRequestBuilders.get("/patients/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath(".error").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath(".message").value("Patient not found"));
    }

    @Test
    void createPatient_shouldOk() throws Exception {
        PatientDto patientDto = PatientDto.builder()
                .patientId("dummnyPatientId1")
                .firstName("Fram")
                .lastName("Arn")
                .dateOfBirth("1990-09-30")
                .gender("M")
                .address("some street")
                .suburbId(2)
                .postcode("2000")
                .phoneNumber("+6180228282")
                .build();
        PatientDto patientDtoResult = PatientDto.builder()
                .id(1L)
                .patientId("dummnyPatientId1")
                .firstName("Fram")
                .lastName("Arn")
                .dateOfBirth("1990-09-30")
                .gender("M")
                .address("some street")
                .suburbId(2)
                .postcode("2000")
                .phoneNumber("+6180228282")
                .build();
        Mockito.when(patientService.savePatient(patientDto)).thenReturn(patientDtoResult);
        mockMvc.perform(MockMvcRequestBuilders.post("/patients")
                        .contentType("application/json")
                        .content("{\n" +
                                "    \"patientId\": \"dummnyPatientId1\",\n" +
                                "    \"firstName\": \"Fram\",\n" +
                                "    \"lastName\": \"Arn\",\n" +
                                "    \"dateOfBirth\": \"1990-09-30\",\n" +
                                "    \"gender\": \"M\",\n" +
                                "    \"address\": \"some street\",\n" +
                                "    \"suburbId\": 2,\n" +
                                "    \"postcode\": \"2000\",\n" +
                                "    \"phoneNumber\":\"+6180228282\"\n" +
                                "}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath(".id").value(1));
    }

    @Test
    void createPatient_shouldBadRequest() throws Exception {
        //assume the patient id and patient gender is not sent
        mockMvc.perform(MockMvcRequestBuilders.post("/patients")
                        .contentType("application/json")
                        .content("{\n" +
                                "    \"firstName\": \"Fram\",\n" +
                                "    \"lastName\": \"Arn\",\n" +
                                "    \"dateOfBirth\": \"1990-09-30\",\n" +
                                "    \"address\": \"some street\",\n" +
                                "    \"suburbId\": 2,\n" +
                                "    \"postcode\": \"2000\",\n" +
                                "    \"phoneNumber\":\"+6180228282\"\n" +
                                "}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath(".gender").value("Gender must be provided"))
                .andExpect(MockMvcResultMatchers.jsonPath(".patientId").value("Patient Id must be provided"));
    }
}