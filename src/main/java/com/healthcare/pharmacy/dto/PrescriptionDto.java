package com.healthcare.pharmacy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionDto {
    private Long id;

    private String drugName;

    private Long refill;

    private Long quantity;

    private Long patientId;
}
