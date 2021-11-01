package com.eleks.academy.pharmagator.controllers;

import com.eleks.academy.pharmagator.projections.PharmacyLight;
import com.eleks.academy.pharmagator.repositories.PharmacyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pharmacies")
public class PharmacyController {

    private final PharmacyRepository pharmacyRepository;

    @GetMapping
    public List<PharmacyLight> getAll() {
        return this.pharmacyRepository.findAllLight();
    }

}
