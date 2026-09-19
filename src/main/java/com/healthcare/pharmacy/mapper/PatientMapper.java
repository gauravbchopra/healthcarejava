package com.healthcare.pharmacy.mapper;

import com.healthcare.pharmacy.dto.PatientDto;
import com.healthcare.pharmacy.entity.Patient;

public class PatientMapper {
    public static PatientDto mapToPatientDto(Patient patient) {
        return new PatientDto(
                patient.getId(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getEmail(),
                patient.getPrescriptions()
                );
    }

    public static Patient mapToPatient(PatientDto patientDto) {
        return new Patient(
                patientDto.getId(),
                patientDto.getFirstName(),
                patientDto.getLastName(),
                patientDto.getEmail(),
                patientDto.getPrescriptions()
                );
    }
}
