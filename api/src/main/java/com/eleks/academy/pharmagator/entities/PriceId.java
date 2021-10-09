package com.eleks.academy.pharmagator.entities;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class PriceId implements Serializable {
    private long pharmacyId;
    private long medicineId;

    public PriceId(long pharmacyId, long medicineId) {
        this.pharmacyId=pharmacyId;
        this.medicineId=medicineId;
    }
}
