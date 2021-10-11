package com.eleks.academy.pharmagator.controllers;

import com.eleks.academy.pharmagator.entities.Pharmacy;
import com.eleks.academy.pharmagator.repositories.PharmacyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pharmacies")
public class PharmacyController {
    private final PharmacyRepository pharmacyRepository;

    @GetMapping
    public List<Pharmacy> getAll() {
        return pharmacyRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Pharmacy> getById(@PathVariable("id") Long id) {
        return this.pharmacyRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Pharmacy> create(@RequestBody Pharmacy pharmacy) {
        return ResponseEntity.ok(pharmacyRepository.save(pharmacy));
    }

    @RequestMapping(method = RequestMethod.PUT, name = "/{id}")
    public ResponseEntity<Pharmacy> update(@PathVariable("id") long id, @RequestBody Pharmacy pharmacy) {
        return this.pharmacyRepository.findById(id)
                .map(s -> pharmacyRepository.save(pharmacy))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        pharmacyRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
