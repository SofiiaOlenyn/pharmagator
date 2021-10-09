package com.eleks.academy.pharmagator.controllers;

import com.eleks.academy.pharmagator.entities.Medicine;
import com.eleks.academy.pharmagator.repositories.MedicineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/medicines")
public class MedicineController {
    private final MedicineRepository medicineRepository;

    @GetMapping
    public ResponseEntity<List<Medicine>> getAll() {
        return ResponseEntity.ok(medicineRepository.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Optional<Medicine>> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(medicineRepository.findById(id));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/addMedicine")
    public ResponseEntity<Medicine> create(@RequestBody Medicine medicine) {
        return ResponseEntity.ok(medicineRepository.save(medicine));
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Medicine> update(@PathVariable("id") long id, @RequestBody Medicine medicine) {
        medicine.setId(id);
        return ResponseEntity.ok(medicineRepository.save(medicine));

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteById(@PathVariable("id") Long id) {
        medicineRepository.deleteById(id);
        return ResponseEntity.ok(id + " is deleted");
    }

}
