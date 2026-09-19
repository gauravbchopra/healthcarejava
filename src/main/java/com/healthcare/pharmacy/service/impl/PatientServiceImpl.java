package com.healthcare.pharmacy.service.impl;

import com.healthcare.pharmacy.dto.PatientDto;
import com.healthcare.pharmacy.entity.Patient;
import com.healthcare.pharmacy.exception.PatientNotFoundException;
import com.healthcare.pharmacy.mapper.PatientMapper;
import com.healthcare.pharmacy.repository.PatientRepository;
import com.healthcare.pharmacy.service.PatientService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PatientServiceImpl implements PatientService {
    private PatientRepository patientRepository;

    @Override
    public PatientDto createPatient(PatientDto patientDto) {
        Patient patient = PatientMapper.mapToPatient(patientDto);
        Patient savedPatient = patientRepository.save(patient);
        return PatientMapper.mapToPatientDto(savedPatient);
    }

    @Override
    public PatientDto updatePatient(Long id, PatientDto patientDto) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException(id));

        patient.setFirstName(patientDto.getFirstName());
        patient.setLastName(patientDto.getLastName());
        patient.setEmail(patientDto.getEmail());

        Patient updatedPatient = patientRepository.save(patient);
        return PatientMapper.mapToPatientDto(updatedPatient);
    }

    @Override
    public void deletePatient(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException(id));

        patientRepository.delete(patient);
    }
}
