package com.eleks.academy.pharmagator.controllers;

import com.eleks.academy.pharmagator.entities.Pharmacy;
import com.eleks.academy.pharmagator.repositories.PharmacyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pharmacies")
public class PharmacyController {
    private final PharmacyRepository pharmacyRepository;

    @GetMapping
    public ResponseEntity<List<Pharmacy>> getAll() {
        return ResponseEntity.ok(pharmacyRepository.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Optional<Pharmacy>> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(pharmacyRepository.findById(id));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/addPharmacy")
    public ResponseEntity<Pharmacy> create(@RequestBody Pharmacy pharmacy) {
        return ResponseEntity.ok(pharmacyRepository.save(pharmacy));
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Pharmacy> update(@PathVariable("id") long id, @RequestBody Pharmacy pharmacy) {
        pharmacy.setId(id);
        return ResponseEntity.ok(pharmacyRepository.save(pharmacy));

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteById(@PathVariable("id") Long id) {
        pharmacyRepository.deleteById(id);
        return ResponseEntity.ok(id + " is deleted");
    }

}
