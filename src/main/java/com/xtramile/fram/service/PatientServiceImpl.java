package com.xtramile.fram.service;

import com.xtramile.fram.model.dto.PatientDto;
import com.xtramile.fram.model.entity.Patient;
import com.xtramile.fram.model.entity.Suburb;
import com.xtramile.fram.repository.PatientRepository;
import com.xtramile.fram.repository.SuburbRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService{
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private SuburbRepository suburbRepository;

    @Transactional
    @Override
    public PatientDto savePatient(PatientDto patientDto) {
        Optional<Patient> patientOpt = patientRepository.findByPatientId(patientDto.getPatientId());
        if(patientOpt.isPresent()){
            throw new EntityExistsException("Patient exists");
        }
        Patient.PatientBuilder patientBuilder = Patient.builder()
                .patientId(patientDto.getPatientId())
                .firstName(patientDto.getFirstName())
                .lastName(patientDto.getLastName())
                .dateOfBirth(getLocalDate(patientDto.getDateOfBirth()))
                .gender(patientDto.getGender())
                .phoneNumber(patientDto.getPhoneNumber())
                .address(patientDto.getAddress())
                .suburb(getSuburb(patientDto.getSuburbId()))
                .postcode(patientDto.getPostcode());
        Patient patient = patientRepository.save(patientBuilder.build());
        patientDto.setId(patient.getId());
        return patientDto;
    }

    @Transactional
    @Override
    public PatientDto updatePatient(PatientDto patientDto) {
        Optional<Patient> patientOpt = patientRepository.findById(patientDto.getId());
        Patient patient = patientOpt.orElseThrow(() -> new EntityNotFoundException("Patient not found"));
        patient.setPatientId(patientDto.getPatientId());
        patient.setFirstName(patientDto.getFirstName());
        patient.setLastName(patientDto.getLastName());
        patient.setDateOfBirth(getLocalDate(patientDto.getDateOfBirth()));
        patient.setGender(patientDto.getGender());
        patient.setPhoneNumber(patientDto.getPhoneNumber());
        patient.setAddress(patientDto.getAddress());
        patient.setSuburb(getSuburb(patientDto.getSuburbId()));
        patient.setPostcode(patientDto.getPostcode());
        return patientDto;
    }

    @Transactional
    @Override
    public void deletePatient(long id) {
        patientRepository.deleteById(id);
    }

    @Override
    public PatientDto getPatient(long id) {
        Optional<Patient> patientOpt = patientRepository.findById(id);
        Patient patient = patientOpt.orElseThrow(() -> new EntityNotFoundException("Patient not found"));
        return convertPatientToPatientDto(patient);
    }

    @Override
    public Page<PatientDto> findPatients(Pageable pageable) {
        return findPatients(null, null, pageable);
    }

    @Override
    public Page<PatientDto> findPatients(String patientId, String name, Pageable pageable) {
        Page<Patient> patientPage;
        if(StringUtils.hasLength(patientId)){
            //search by patient id
            patientPage = patientRepository.findByPatientIdContainingIgnoreCase(patientId, pageable);
        }else if(StringUtils.hasLength(name)){
            //search by name
            patientPage = patientRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name, name, pageable);
        }else{
            //find all
            patientPage = patientRepository.findAll(pageable);
        }
        List<PatientDto> dtos = patientPage.get().map(this::convertPatientToPatientDto).toList();

        return new PageImpl<>(dtos, pageable, patientPage.getTotalElements());
    }

    private LocalDate getLocalDate(String dob) {
        return LocalDate.parse(dob);
    }

    private Suburb getSuburb(Integer suburbId){
        //get suburb
        Optional<Suburb> suburbOptional = suburbRepository.findById(suburbId);
        return suburbOptional.orElseThrow(() -> new EntityNotFoundException("Suburb not found"));
    }

    private PatientDto convertPatientToPatientDto(Patient patient){
        //TODO: it can be improved using MapStruct to map the object
        PatientDto.PatientDtoBuilder builder = PatientDto.builder()
                .id(patient.getId())
                .patientId(patient.getPatientId())
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .fullName(patient.getFirstName() + " " + patient.getLastName())
                .dateOfBirth(patient.getDateOfBirth().toString())
                .gender(patient.getGender())
                .address(patient.getAddress())
                .suburbId(patient.getSuburb().getId())
                .suburbName(patient.getSuburb().getName())
                .stateId(patient.getSuburb().getState().getId())
                .stateName(patient.getSuburb().getState().getName())
                .postcode(patient.getPostcode())
                .phoneNumber(patient.getPhoneNumber());
        return builder.build();
    }
}

