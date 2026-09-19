package com.healthcare.pharmacy.controller;

import com.healthcare.pharmacy.dto.PrescriptionDto;
import com.healthcare.pharmacy.service.PrescriptionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    private PrescriptionService prescriptionService;

    @PostMapping
    public ResponseEntity<PrescriptionDto> createPrescription(@RequestBody PrescriptionDto prescriptionDto) {
        PrescriptionDto savedPrescription = prescriptionService.createPrescription(prescriptionDto);
        return new ResponseEntity<>(savedPrescription, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PrescriptionDto> updatePrescription(
            @PathVariable Long id,
            @RequestBody PrescriptionDto prescriptionDto) {
        PrescriptionDto updatedPrescription = prescriptionService.updatePrescription(id, prescriptionDto);
        return ResponseEntity.ok(updatedPrescription);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrescription(@PathVariable Long id) {
        prescriptionService.deletePrescription(id);
        return ResponseEntity.noContent().build();
    }
}
