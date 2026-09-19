package com.healthcare.pharmacy.service.impl;

import com.healthcare.pharmacy.dto.PatientDto;
import com.healthcare.pharmacy.entity.Patient;
import com.healthcare.pharmacy.exception.PatientNotFoundException;
import com.healthcare.pharmacy.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientServiceImplTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientServiceImpl patientService;

    @Test
    void createPatientSavesPatientAndReturnsDto() {
        PatientDto patientDto = new PatientDto(null, "John", "Doe", "john@example.com", null);
        Patient savedPatient = new Patient(1L, "John", "Doe", "john@example.com", null);

        when(patientRepository.save(org.mockito.ArgumentMatchers.any(Patient.class)))
                .thenReturn(savedPatient);

        PatientDto result = patientService.createPatient(patientDto);

        assertEquals(1L, result.getId());
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("john@example.com", result.getEmail());
        verify(patientRepository).save(org.mockito.ArgumentMatchers.any(Patient.class));
    }

    @Test
    void updatePatientUpdatesExistingPatientAndReturnsDto() {
        Long patientId = 1L;
        Patient existingPatient = new Patient(patientId, "John", "Doe", "john@example.com", null);
        PatientDto updateDto = new PatientDto(null, "Jane", "Smith", "jane@example.com", null);

        when(patientRepository.findById(patientId)).thenReturn(Optional.of(existingPatient));
        when(patientRepository.save(existingPatient)).thenReturn(existingPatient);

        PatientDto result = patientService.updatePatient(patientId, updateDto);

        assertEquals(patientId, result.getId());
        assertEquals("Jane", result.getFirstName());
        assertEquals("Smith", result.getLastName());
        assertEquals("jane@example.com", result.getEmail());
        verify(patientRepository).findById(patientId);
        verify(patientRepository).save(existingPatient);
    }

    @Test
    void updatePatientThrowsWhenPatientDoesNotExist() {
        Long patientId = 99L;
        PatientDto updateDto = new PatientDto(null, "Jane", "Smith", "jane@example.com", null);

        when(patientRepository.findById(patientId)).thenReturn(Optional.empty());

        assertThrows(
                PatientNotFoundException.class,
                () -> patientService.updatePatient(patientId, updateDto)
        );

        verify(patientRepository).findById(patientId);
    }

    @Test
    void deletePatientDeletesExistingPatient() {
        Long patientId = 1L;
        Patient patient = new Patient(patientId, "John", "Doe", "john@example.com", null);

        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));

        patientService.deletePatient(patientId);

        verify(patientRepository).findById(patientId);
        verify(patientRepository).delete(patient);
    }

    @Test
    void deletePatientThrowsWhenPatientDoesNotExist() {
        Long patientId = 99L;

        when(patientRepository.findById(patientId)).thenReturn(Optional.empty());

        assertThrows(
                PatientNotFoundException.class,
                () -> patientService.deletePatient(patientId)
        );

        verify(patientRepository).findById(patientId);
    }
}
