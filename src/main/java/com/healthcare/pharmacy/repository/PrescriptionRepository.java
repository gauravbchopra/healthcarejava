package com.healthcare.pharmacy.repository;

import com.healthcare.pharmacy.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
}
