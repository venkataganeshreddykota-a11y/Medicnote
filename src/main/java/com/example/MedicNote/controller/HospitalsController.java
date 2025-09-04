package com.example.MedicNote.controller;

import com.example.MedicNote.entity.Hospitals;
import com.example.MedicNote.service.HospitalsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/hospitals")
@Tag(name = "Hospitals")
public class HospitalsController {
    @Autowired
    private HospitalsService hospitalsService;

    @GetMapping
    public List<Hospitals> getAllHospitals() {
        return hospitalsService.getAllHospitals();
    }

    @GetMapping("/{id}")
    public Optional<Hospitals> getHospitalById(@PathVariable String id) {
        return hospitalsService.getHospitalById(id);
    }

    @PostMapping
    public Hospitals createHospital(@RequestBody Hospitals hospital) {
        return hospitalsService.saveHospital(hospital);
    }

    @PutMapping("/{id}")
    public Hospitals updateHospital(@PathVariable String id, @RequestBody Hospitals hospital) {
        hospital.setHospitalId(id);
        return hospitalsService.saveHospital(hospital);
    }

    @DeleteMapping("/{id}")
    public void deleteHospital(@PathVariable String id) {
        hospitalsService.deleteHospital(id);
    }
} 