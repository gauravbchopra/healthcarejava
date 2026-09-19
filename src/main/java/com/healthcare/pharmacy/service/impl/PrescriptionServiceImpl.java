package com.healthcare.pharmacy.service.impl;

import com.healthcare.pharmacy.dto.PrescriptionDto;
import com.healthcare.pharmacy.entity.Patient;
import com.healthcare.pharmacy.entity.Prescription;
import com.healthcare.pharmacy.exception.PatientNotFoundException;
import com.healthcare.pharmacy.exception.PrescriptionNotFoundException;
import com.healthcare.pharmacy.mapper.PrescriptionMapper;
import com.healthcare.pharmacy.repository.PatientRepository;
import com.healthcare.pharmacy.repository.PrescriptionRepository;
import com.healthcare.pharmacy.service.PrescriptionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PrescriptionServiceImpl implements PrescriptionService {
    private PrescriptionRepository prescriptionRepository;
    private PatientRepository patientRepository;

    @Override
    public PrescriptionDto createPrescription(PrescriptionDto prescriptionDto) {
        Patient patient = findPatient(prescriptionDto.getPatientId());
        Prescription prescription = PrescriptionMapper.mapToPrescription(prescriptionDto, patient);
        Prescription savedPrescription = prescriptionRepository.save(prescription);
        return PrescriptionMapper.mapToPrescriptionDto(savedPrescription);
    }

    @Override
    public PrescriptionDto updatePrescription(Long id, PrescriptionDto prescriptionDto) {
        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() -> new PrescriptionNotFoundException(id));
        Patient patient = findPatient(prescriptionDto.getPatientId());

        prescription.setDrugName(prescriptionDto.getDrugName());
        prescription.setRefill(prescriptionDto.getRefill());
        prescription.setQuantity(prescriptionDto.getQuantity());
        prescription.setPatient(patient);

        Prescription updatedPrescription = prescriptionRepository.save(prescription);
        return PrescriptionMapper.mapToPrescriptionDto(updatedPrescription);
    }

    @Override
    public void deletePrescription(Long id) {
        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() -> new PrescriptionNotFoundException(id));

        prescriptionRepository.delete(prescription);
    }

    private Patient findPatient(Long patientId) {
        return patientRepository.findById(patientId)
                .orElseThrow(() -> new PatientNotFoundException(patientId));
    }
}
