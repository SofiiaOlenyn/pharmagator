package com.eleks.academy.pharmagator.controllers;

import com.eleks.academy.pharmagator.entities.Price;
import com.eleks.academy.pharmagator.entities.PriceId;
import com.eleks.academy.pharmagator.repositories.PriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/prices")
public class PriceController {
    private final PriceRepository priceRepository;

    @GetMapping
    public ResponseEntity<List<Price>> getAll() {
        return ResponseEntity.ok(priceRepository.findAll());
    }

    @GetMapping(value = "/{pharmacyId}/{medicineId}")
    public ResponseEntity<Optional<Price>> getById(@PathVariable("pharmacyId") long pharmacyId,
                                                   @PathVariable("medicineId") long medicineId) {
        return ResponseEntity.ok(priceRepository.findById(new PriceId(pharmacyId, medicineId)));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/addPrice")
    public ResponseEntity<Price> create(@RequestBody Price price) {
        return ResponseEntity.ok(priceRepository.save(price));
    }

    @PostMapping("/update/{pharmacyId}/{medicineId}")
    public ResponseEntity<Price> update(@PathVariable("pharmacyId") long pharmacyId,
                                        @PathVariable("medicineId") long medicineId,
                                        @RequestBody Price price) {
        price.setMedicineId(medicineId);
        price.setPharmacyId(pharmacyId);
        return ResponseEntity.ok(priceRepository.save(price));

    }

    @RequestMapping(value = "/{pharmacyId}/{medicineId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteById(@PathVariable("pharmacyId") long pharmacyId,
                                     @PathVariable("medicineId") long medicineId) {
        priceRepository.deleteById(new PriceId(pharmacyId, medicineId));
        return ResponseEntity.ok(new PriceId(pharmacyId, medicineId) + " is deleted");
    }
}
