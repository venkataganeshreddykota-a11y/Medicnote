package com.example.MedicNote.controller;

import com.example.MedicNote.entity.Doctors;
import com.example.MedicNote.service.DoctorsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/doctors")
@Tag(name = "Doctors")
public class DoctorsController {
    @Autowired
    private DoctorsService doctorsService;

    @GetMapping
    public List<Doctors> getAllDoctors() {
        System.out.println("[DEBUG] getAllDoctors called");
        List<Doctors> doctors = doctorsService.getAllDoctors();
        System.out.println("[DEBUG] getAllDoctors returned " + doctors.size() + " doctors");
        return doctors;
    }

    @GetMapping("/{id}")
    public Optional<Doctors> getDoctorById(@PathVariable String id) {
        return doctorsService.getDoctorById(id);
    }

    // Public endpoint for patients to view available doctors
    @GetMapping("/public")
    public List<Doctors> getPublicDoctors() {
        return doctorsService.getAllDoctors();
    }

    // Hospital-specific doctors for receptionists
    @GetMapping("/hospital/{hospitalId}")
    public List<Doctors> getDoctorsByHospital(@PathVariable String hospitalId) {
        System.out.println("[DEBUG] Getting doctors for hospital: " + hospitalId);
        List<Doctors> doctors = doctorsService.getDoctorsByHospital(hospitalId);
        System.out.println("[DEBUG] Found " + doctors.size() + " doctors for hospital: " + hospitalId);
        return doctors;
    }
    
    // Test endpoint to check all doctors
    @GetMapping("/test/all")
    public Map<String, Object> testAllDoctors() {
        List<Doctors> allDoctors = doctorsService.getAllDoctors();
        Map<String, Object> response = new HashMap<>();
        response.put("totalDoctors", allDoctors.size());
        response.put("doctors", allDoctors.stream().map(d -> {
            Map<String, Object> doctor = new HashMap<>();
            doctor.put("doctorId", d.getDoctorId());
            doctor.put("hospitalId", d.getHospital() != null ? d.getHospital().getHospitalId() : null);
            return doctor;
        }).collect(java.util.stream.Collectors.toList()));
        return response;
    }

    @PostMapping
    public Doctors createDoctor(@RequestBody Doctors doctor) {
        return doctorsService.saveDoctor(doctor);
    }

    @PutMapping("/{id}")
    public Doctors updateDoctor(@PathVariable String id, @RequestBody Doctors doctor) {
        doctor.setDoctorId(id);
        return doctorsService.saveDoctor(doctor);
    }

    @DeleteMapping("/{id}")
    public void deleteDoctor(@PathVariable String id) {
        doctorsService.deleteDoctor(id);
    }
} 