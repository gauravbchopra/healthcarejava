package com.healthcare.pharmacy.service.impl;

import com.healthcare.pharmacy.dto.PrescriptionDto;
import com.healthcare.pharmacy.entity.Patient;
import com.healthcare.pharmacy.entity.Prescription;
import com.healthcare.pharmacy.exception.PatientNotFoundException;
import com.healthcare.pharmacy.exception.PrescriptionNotFoundException;
import com.healthcare.pharmacy.repository.PatientRepository;
import com.healthcare.pharmacy.repository.PrescriptionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PrescriptionServiceImplTest {

    @Mock
    private PrescriptionRepository prescriptionRepository;

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PrescriptionServiceImpl prescriptionService;

    @Test
    void createPrescriptionFindsPatientAndSetsItOnPrescription() {
        Long patientId = 1L;
        Patient patient = new Patient(patientId, "John", "Doe", "john@example.com", null);
        PrescriptionDto prescriptionDto = new PrescriptionDto(
                null, "Aspirin", 2L, 10L, patientId);
        Prescription savedPrescription = new Prescription(
                5L, "Aspirin", 2L, 10L, patient);

        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));
        when(prescriptionRepository.save(org.mockito.ArgumentMatchers.any(Prescription.class)))
                .thenReturn(savedPrescription);

        PrescriptionDto result = prescriptionService.createPrescription(prescriptionDto);

        assertEquals(5L, result.getId());
        assertEquals(patientId, result.getPatientId());

        ArgumentCaptor<Prescription> captor = ArgumentCaptor.forClass(Prescription.class);
        verify(prescriptionRepository).save(captor.capture());
        assertEquals(patient, captor.getValue().getPatient());
    }

    @Test
    void createPrescriptionThrowsWhenPatientDoesNotExist() {
        Long patientId = 99L;
        PrescriptionDto prescriptionDto = new PrescriptionDto(
                null, "Aspirin", 2L, 10L, patientId);

        when(patientRepository.findById(patientId)).thenReturn(Optional.empty());

        assertThrows(
                PatientNotFoundException.class,
                () -> prescriptionService.createPrescription(prescriptionDto)
        );

        verify(patientRepository).findById(patientId);
    }

    @Test
    void updatePrescriptionUpdatesFieldsAndPatient() {
        Long prescriptionId = 5L;
        Long patientId = 2L;
        Patient patient = new Patient(patientId, "Jane", "Smith", "jane@example.com", null);
        Prescription prescription = new Prescription(
                prescriptionId, "Aspirin", 2L, 10L, patient);
        PrescriptionDto updateDto = new PrescriptionDto(
                null, "Ibuprofen", 1L, 20L, patientId);

        when(prescriptionRepository.findById(prescriptionId)).thenReturn(Optional.of(prescription));
        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));
        when(prescriptionRepository.save(prescription)).thenReturn(prescription);

        PrescriptionDto result = prescriptionService.updatePrescription(prescriptionId, updateDto);

        assertEquals(prescriptionId, result.getId());
        assertEquals("Ibuprofen", result.getDrugName());
        assertEquals(1L, result.getRefill());
        assertEquals(20L, result.getQuantity());
        assertEquals(patientId, result.getPatientId());
        verify(prescriptionRepository).findById(prescriptionId);
        verify(patientRepository).findById(patientId);
        verify(prescriptionRepository).save(prescription);
    }

    @Test
    void updatePrescriptionThrowsWhenPrescriptionDoesNotExist() {
        Long prescriptionId = 99L;
        PrescriptionDto updateDto = new PrescriptionDto(null, "Ibuprofen", 1L, 20L, 1L);

        when(prescriptionRepository.findById(prescriptionId)).thenReturn(Optional.empty());

        assertThrows(
                PrescriptionNotFoundException.class,
                () -> prescriptionService.updatePrescription(prescriptionId, updateDto)
        );

        verify(prescriptionRepository).findById(prescriptionId);
    }

    @Test
    void deletePrescriptionDeletesExistingPrescription() {
        Long prescriptionId = 5L;
        Prescription prescription = new Prescription(
                prescriptionId, "Aspirin", 2L, 10L, new Patient());

        when(prescriptionRepository.findById(prescriptionId)).thenReturn(Optional.of(prescription));

        prescriptionService.deletePrescription(prescriptionId);

        verify(prescriptionRepository).findById(prescriptionId);
        verify(prescriptionRepository).delete(prescription);
    }

    @Test
    void deletePrescriptionThrowsWhenPrescriptionDoesNotExist() {
        Long prescriptionId = 99L;

        when(prescriptionRepository.findById(prescriptionId)).thenReturn(Optional.empty());

        assertThrows(
                PrescriptionNotFoundException.class,
                () -> prescriptionService.deletePrescription(prescriptionId)
        );

        verify(prescriptionRepository).findById(prescriptionId);
    }
}
