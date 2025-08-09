package com.xtramile.fram.controller;

import com.xtramile.fram.model.dto.PatientDto;
import com.xtramile.fram.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/patients")
public class PatientController {
    @Autowired
    private PatientService patientService;

    @GetMapping("/{id}")
    public PatientDto getPatient(@PathVariable Long id){
        return patientService.getPatient(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PatientDto createPatient(@RequestBody @Valid PatientDto patientDto){
        return patientService.savePatient(patientDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public PatientDto updatePatient(@RequestBody @Valid PatientDto patientDto){
        return patientService.updatePatient(patientDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePatient(@PathVariable Long id){
        patientService.deletePatient(id);
    }

    @GetMapping("/search")
    public Page<PatientDto> searchPatient(@RequestParam(name = "patientId", required = false) String patientId,
                                          @RequestParam(name = "name", required = false) String name,
                                          Pageable pageable){
        return patientService.findPatients(patientId, name, pageable);
    }

    @GetMapping
    public Page<PatientDto> getPatients(Pageable pageable){
        return patientService.findPatients(pageable);
    }
}
