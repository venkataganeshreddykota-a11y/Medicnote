package com.example.MedicNote.controller;

import com.example.MedicNote.entity.Patients;
import com.example.MedicNote.service.PatientsService;
import com.example.MedicNote.repository.PatientsRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/patients")
@Tag(name = "Patients")
public class PatientsController {
    @Autowired
    private PatientsService patientsService;
    @Autowired
    private PatientsRepository patientsRepository;

    @GetMapping
    public List<Patients> getAllPatients() {
        // Debug logging for authorities
        org.springframework.security.core.Authentication auth = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authorities: " + auth.getAuthorities());
        System.out.println("[DEBUG] getAllPatients called");
        List<Patients> patients = patientsService.getAllPatients();
        System.out.println("[DEBUG] getAllPatients returned " + patients.size() + " patients");
        return patients;
    }

    @GetMapping("/{id}")
    public Optional<Patients> getPatientById(@PathVariable String id) {
        return patientsService.getPatientById(id);
    }

    @PostMapping
    public Patients createPatient(@RequestBody Patients patient) {
        return patientsService.savePatient(patient);
    }

    @PutMapping("/{id}")
    public Patients updatePatient(@PathVariable String id, @RequestBody Patients patient) {
        patient.setPatientId(id);
        return patientsService.savePatient(patient);
    }

    @DeleteMapping("/{id}")
    public void deletePatient(@PathVariable String id) {
        patientsService.deletePatient(id);
    }

    // Hospital-specific patients for receptionists (patients who have appointments with doctors in the hospital)
    @GetMapping("/hospital/{hospitalId}")
    public List<Patients> getPatientsByHospital(@PathVariable String hospitalId) {
        System.out.println("[DEBUG] Getting patients for hospital: " + hospitalId);
        List<Patients> patients = patientsService.getPatientsByHospital(hospitalId);
        System.out.println("[DEBUG] Found " + patients.size() + " patients for hospital: " + hospitalId);
        return patients;
    }
    
    // Test endpoint to check appointment count
    @GetMapping("/hospital/{hospitalId}/appointment-count")
    public Map<String, Object> getAppointmentCountByHospital(@PathVariable String hospitalId) {
        Long count = patientsRepository.countAppointmentsByHospital(hospitalId);
        Map<String, Object> response = new HashMap<>();
        response.put("hospitalId", hospitalId);
        response.put("appointmentCount", count);
        return response;
    }
} 