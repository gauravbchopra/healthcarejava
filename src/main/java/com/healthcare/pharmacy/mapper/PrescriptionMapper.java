package com.healthcare.pharmacy.mapper;

import com.healthcare.pharmacy.dto.PrescriptionDto;
import com.healthcare.pharmacy.entity.Patient;
import com.healthcare.pharmacy.entity.Prescription;

public class PrescriptionMapper {
    public static PrescriptionDto mapToPrescriptionDto(Prescription prescription) {
        return new PrescriptionDto(
                prescription.getId(),
                prescription.getDrugName(),
                prescription.getRefill(),
                prescription.getQuantity(),
                prescription.getPatient() == null ? null : prescription.getPatient().getId()
                );
    }

    public static Prescription mapToPrescription(PrescriptionDto prescriptionDto, Patient patient) {
        return new Prescription(
                prescriptionDto.getId(),
                prescriptionDto.getDrugName(),
                prescriptionDto.getRefill(),
                prescriptionDto.getQuantity(),
                patient
                );
    }
}
