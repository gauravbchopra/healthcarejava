package com.healthcare.pharmacy.repository;

import com.healthcare.pharmacy.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
