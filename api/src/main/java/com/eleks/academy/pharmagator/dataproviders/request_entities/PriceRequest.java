package com.eleks.academy.pharmagator.dataproviders.request_entities;


import com.eleks.academy.pharmagator.entities.Price;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;

@Data
public class PriceRequest {

    @NotNull
    private long pharmacyId;
    @NotNull
    private long medicineId;
    @NotNull
    private BigDecimal price;
    @NotNull
    private String externalId;
    @NotNull
    private Instant updatedAt;

    public Price toPrice() {
        Price price = new Price();
        price.setPharmacyId(pharmacyId);
        price.setMedicineId(medicineId);
        price.setPrice(this.price);
        price.setExternalId(externalId);
        price.setUpdatedAt(updatedAt);
        return price;
    }
}
