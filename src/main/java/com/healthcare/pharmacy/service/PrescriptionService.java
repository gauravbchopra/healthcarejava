package com.healthcare.pharmacy.service;

import com.healthcare.pharmacy.dto.PrescriptionDto;

public interface PrescriptionService {
    PrescriptionDto createPrescription(PrescriptionDto prescriptionDto);

    PrescriptionDto updatePrescription(Long id, PrescriptionDto prescriptionDto);

    void deletePrescription(Long id);
}
