package com.healthcare.pharmacy.service;

import com.healthcare.pharmacy.dto.PatientDto;

public interface PatientService {
    PatientDto createPatient(PatientDto patientDto);

    PatientDto updatePatient(Long id, PatientDto patientDto);

    void deletePatient(Long id);
}
