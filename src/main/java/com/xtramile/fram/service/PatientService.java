package com.xtramile.fram.service;

import com.xtramile.fram.model.dto.PatientDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PatientService {
    /**
     * Save patient method
     * @param patientDto
     * @return
     */
    PatientDto savePatient(PatientDto patientDto);

    /**
     * Update patient method
     * @param patientDto
     * @return
     */
    PatientDto updatePatient(PatientDto patientDto);

    /**
     * Delete patient method. This method will do the hard delete of the patient record.
     * @param id
     */
    void deletePatient(long id);

    /**
     * Get patient record by id.
     * @param id
     * @return
     */
    PatientDto getPatient(long id);

    Page<PatientDto> findPatients(Pageable pageable);
    Page<PatientDto> findPatients(String patientId, String name, Pageable pageable);
}
