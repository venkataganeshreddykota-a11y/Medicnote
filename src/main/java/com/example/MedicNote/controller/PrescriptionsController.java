package com.example.MedicNote.controller;

import com.example.MedicNote.entity.Prescriptions;
import com.example.MedicNote.service.PrescriptionsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/prescriptions")
@Tag(name = "Prescriptions")
public class PrescriptionsController {
    @Autowired
    private PrescriptionsService prescriptionsService;

    @GetMapping
    public List<Prescriptions> getAllPrescriptions() {
        return prescriptionsService.getAllPrescriptions();
    }

    @GetMapping("/{id}")
    public Optional<Prescriptions> getPrescriptionById(@PathVariable String id) {
        return prescriptionsService.getPrescriptionById(id);
    }

    @PostMapping
    public Prescriptions createPrescription(@RequestBody Prescriptions prescription) {
        return prescriptionsService.savePrescription(prescription);
    }

    @PutMapping("/{id}")
    public Prescriptions updatePrescription(@PathVariable String id, @RequestBody Prescriptions prescription) {
        prescription.setPrescriptionId(id);
        return prescriptionsService.savePrescription(prescription);
    }

    @DeleteMapping("/{id}")
    public void deletePrescription(@PathVariable String id) {
        prescriptionsService.deletePrescription(id);
    }
} 