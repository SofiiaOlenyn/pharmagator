package com.eleks.academy.pharmagator.controllers;

import com.eleks.academy.pharmagator.entities.Price;
import com.eleks.academy.pharmagator.entities.PriceId;
import com.eleks.academy.pharmagator.repositories.MedicineRepository;
import com.eleks.academy.pharmagator.repositories.PharmacyRepository;
import com.eleks.academy.pharmagator.repositories.PriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/prices")
public class PriceController {
    private final PriceRepository priceRepository;
    private final MedicineRepository medicineRepository;
    private final PharmacyRepository pharmacyRepository;

    @GetMapping
    public List<Price> getAll() {
        return priceRepository.findAll();
    }

    @GetMapping(value = "/{pharmacyId}/{medicineId}")
    public ResponseEntity<Price> getById(@PathVariable("pharmacyId") long pharmacyId,
                                         @PathVariable("medicineId") long medicineId) {
        return this.priceRepository.findById(new PriceId(pharmacyId, medicineId))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Price> create(@RequestBody Price price) {

        if (this.pharmacyRepository.findById(price.getPharmacyId()).isEmpty() ||
                this.medicineRepository.findById(price.getMedicineId()).isEmpty())
            return ResponseEntity.unprocessableEntity().build();

        return ResponseEntity.ok(priceRepository.save(price));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{pharmacyId}/{medicineId}")
    public ResponseEntity<Price> update(@PathVariable("pharmacyId") long pharmacyId,
                                        @PathVariable("medicineId") long medicineId,
                                        @RequestBody Price price) {
        return this.priceRepository.findById(new PriceId(pharmacyId, medicineId))
                .map(s -> priceRepository.save(price))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @RequestMapping(value = "/{pharmacyId}/{medicineId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteById(@PathVariable("pharmacyId") long pharmacyId,
                                     @PathVariable("medicineId") long medicineId) {
        priceRepository.deleteById(new PriceId(pharmacyId, medicineId));
        return ResponseEntity.noContent().build();
    }
}
