package com.xtramile.fram.service;

import com.xtramile.fram.model.dto.PatientDto;
import com.xtramile.fram.model.entity.Patient;
import com.xtramile.fram.model.entity.Suburb;
import com.xtramile.fram.repository.PatientRepository;
import com.xtramile.fram.repository.SuburbRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceImplTest {
    @Mock
    PatientRepository patientRepository;
    @Mock
    SuburbRepository suburbRepository;
    @InjectMocks
    PatientServiceImpl patientService;

    @Test
    void savePatient_shouldOk() {
        PatientDto patientDto = PatientDto.builder()
                .patientId("dummyPatientId1")
                .firstName("dummyFirstName")
                .dateOfBirth("1990-06-30")
                .suburbId(1).build();
        //patient id not exist
        when(patientRepository.findByPatientId("dummyPatientId1")).thenReturn(Optional.empty());
        //suburb exist
        when(suburbRepository.findById(1)).thenReturn(Optional.of(new Suburb()));
        //save to db
        Patient patient = Patient.builder().id(90L).build();
        when(patientRepository.save(any())).thenReturn(patient);
        PatientDto result = patientService.savePatient(patientDto);

        //the dto should include the record id.
        Assertions.assertEquals(90L, result.getId());
    }

    @Test
    void savePatient_shouldReturnEntityExistException() {
        PatientDto patientDto = PatientDto.builder()
                .patientId("dummyPatientId1")
                .firstName("dummyFirstName")
                .dateOfBirth("1990-06-30")
                .suburbId(1).build();
        //patient id exist
        when(patientRepository.findByPatientId("dummyPatientId1")).thenReturn(Optional.of(new Patient()));

        EntityExistsException exception = assertThrows(EntityExistsException.class, () -> patientService.savePatient(patientDto));
        Assertions.assertEquals("Patient exists", exception.getMessage());
    }

    @Test
    void updatePatient_shouldOk() {
        PatientDto patientDto = PatientDto.builder()
                .id(10L)
                .patientId("dummyPatientId1")
                .firstName("dummyFirstName")
                .dateOfBirth("1990-06-30")
                .suburbId(1).build();
        when(patientRepository.findById(10L)).thenReturn(Optional.of(new Patient()));
        when(suburbRepository.findById(1)).thenReturn(Optional.of(new Suburb()));
        patientService.updatePatient(patientDto);

        //check if the method calls patient repository with id 10L
        verify(patientRepository, times(1)).findById(10L);
    }

    @Test
    void updatePatient_shouldReturnEntityNotFoundException() {
        PatientDto patientDto = PatientDto.builder()
                .id(10L)
                .patientId("dummyPatientId1")
                .firstName("dummyFirstName")
                .dateOfBirth("1990-06-30")
                .suburbId(1).build();
        when(patientRepository.findById(10L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> patientService.updatePatient(patientDto));
        Assertions.assertEquals("Patient not found", exception.getMessage());
    }
}