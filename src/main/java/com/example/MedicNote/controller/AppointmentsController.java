package com.example.MedicNote.controller;

import com.example.MedicNote.dto.AppointmentsDTO;
import com.example.MedicNote.entity.Appointments;
import com.example.MedicNote.entity.Doctors;
import com.example.MedicNote.entity.Patients;
import com.example.MedicNote.entity.AvailableSlots;
import com.example.MedicNote.service.AppointmentsService;
import com.example.MedicNote.service.DoctorsService;
import com.example.MedicNote.service.PatientsService;
import com.example.MedicNote.service.AvailableSlotsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/appointments")
@Tag(name = "Appointments")
public class AppointmentsController {
    @Autowired
    private AppointmentsService appointmentsService;
    
    @Autowired
    private DoctorsService doctorsService;
    
    @Autowired
    private PatientsService patientsService;
    
    @Autowired
    private AvailableSlotsService availableSlotsService;

    @GetMapping
    public List<Appointments> getAllAppointments() {
        System.out.println("[DEBUG] AppointmentsController.getAllAppointments - Request received!");
        return appointmentsService.getAllAppointments();
    }

    @GetMapping("/{id}")
    public Optional<Appointments> getAppointmentById(@PathVariable String id) {
        return appointmentsService.getAppointmentById(id);
    }

    @PostMapping
    public Appointments createAppointment(@RequestBody AppointmentsDTO dto) {
        System.out.println("[DEBUG] AppointmentsController.createAppointment - Received DTO: " + dto);
        System.out.println("[DEBUG] AppointmentsController.createAppointment - patientId: " + dto.getPatientId());
        System.out.println("[DEBUG] AppointmentsController.createAppointment - doctorId: " + dto.getDoctorId());
        System.out.println("[DEBUG] AppointmentsController.createAppointment - availableSlotId: " + dto.getAvailableSlotId());
        
        // Convert DTO to entity
        Appointments appointment = new Appointments();
        appointment.setId(UUID.randomUUID().toString());
        
        // Set patient
        Optional<Patients> patient = patientsService.getPatientById(dto.getPatientId());
        if (patient.isPresent()) {
            appointment.setPatient(patient.get());
            System.out.println("[DEBUG] AppointmentsController.createAppointment - Found patient: " + patient.get().getUser().getEmail());
        } else {
            throw new RuntimeException("Patient not found with ID: " + dto.getPatientId());
        }
        
        // Set doctor
        Optional<Doctors> doctor = doctorsService.getDoctorById(dto.getDoctorId());
        if (doctor.isPresent()) {
            appointment.setDoctor(doctor.get());
            System.out.println("[DEBUG] AppointmentsController.createAppointment - Found doctor: " + doctor.get().getUser().getEmail());
        } else {
            throw new RuntimeException("Doctor not found with ID: " + dto.getDoctorId());
        }
        
        // Set other fields
        appointment.setAvailableSlotId(dto.getAvailableSlotId());
        appointment.setType(dto.getType());
        appointment.setDepartment(dto.getDepartment());
        appointment.setStatus(dto.getStatus());
        appointment.setDescription(dto.getDescription());
        
        System.out.println("[DEBUG] AppointmentsController.createAppointment - Created entity: " + appointment);
        
        return appointmentsService.saveAppointment(appointment);
    }

    @PutMapping("/{id}")
    public Appointments updateAppointment(@PathVariable String id, @RequestBody AppointmentsDTO dto) {
        // Get existing appointment
        Optional<Appointments> existingAppointment = appointmentsService.getAppointmentById(id);
        if (!existingAppointment.isPresent()) {
            throw new RuntimeException("Appointment not found with ID: " + id);
        }
        
        Appointments appointment = existingAppointment.get();
        appointment.setId(id);
        
        // Set patient
        Optional<Patients> patient = patientsService.getPatientById(dto.getPatientId());
        if (patient.isPresent()) {
            appointment.setPatient(patient.get());
        } else {
            throw new RuntimeException("Patient not found with ID: " + dto.getPatientId());
        }
        
        // Set doctor
        Optional<Doctors> doctor = doctorsService.getDoctorById(dto.getDoctorId());
        if (doctor.isPresent()) {
            appointment.setDoctor(doctor.get());
        } else {
            throw new RuntimeException("Doctor not found with ID: " + dto.getDoctorId());
        }
        
        // Set other fields
        appointment.setAvailableSlotId(dto.getAvailableSlotId());
        appointment.setType(dto.getType());
        appointment.setDepartment(dto.getDepartment());
        appointment.setStatus(dto.getStatus());
        appointment.setDescription(dto.getDescription());
        
        return appointmentsService.saveAppointment(appointment);
    }

    @DeleteMapping("/{id}")
    public void deleteAppointment(@PathVariable String id) {
        appointmentsService.deleteAppointment(id);
    }

    @PutMapping("/{id}/accept")
    public Appointments acceptAppointment(@PathVariable String id) {
        System.out.println("[DEBUG] AppointmentsController.acceptAppointment - Request received for ID: " + id);
        return appointmentsService.acceptAppointment(id);
    }

    @PutMapping("/{id}/reject")
    public Appointments rejectAppointment(@PathVariable String id) {
        System.out.println("[DEBUG] AppointmentsController.rejectAppointment - Request received for ID: " + id);
        return appointmentsService.rejectAppointment(id);
    }

    // Test endpoint to get IDs for testing
    @GetMapping("/test/ids")
    public Map<String, Object> getTestIds() {
        Map<String, Object> response = new HashMap<>();
        
        // Get all doctors
        List<Doctors> doctors = doctorsService.getAllDoctors();
        if (!doctors.isEmpty()) {
            Doctors doctor = doctors.get(0);
            response.put("doctorId", doctor.getDoctorId());
            response.put("doctorEmail", doctor.getUser().getEmail());
        }
        
        // Get all patients
        List<Patients> patients = patientsService.getAllPatients();
        if (!patients.isEmpty()) {
            Patients patient = patients.get(0);
            response.put("patientId", patient.getPatientId());
            response.put("patientEmail", patient.getUser().getEmail());
        }
        
        // Get all available slots
        List<AvailableSlots> availableSlots = availableSlotsService.getAllAvailableSlots();
        if (!availableSlots.isEmpty()) {
            AvailableSlots slot = availableSlots.get(0);
            response.put("availableSlotId", slot.getId());
        }
        
        return response;
    }

    // Simple test endpoint to check if requests are reaching the controller
    @GetMapping("/test/ping")
    public Map<String, String> ping() {
        System.out.println("[DEBUG] AppointmentsController.ping - Request received!");
        Map<String, String> response = new HashMap<>();
        response.put("message", "pong");
        response.put("timestamp", java.time.LocalDateTime.now().toString());
        return response;
    }

    // Test POST endpoint without authentication
    @PostMapping("/test/post")
    public Map<String, String> testPost(@RequestBody Map<String, Object> body) {
        System.out.println("[DEBUG] AppointmentsController.testPost - Request received!");
        System.out.println("[DEBUG] AppointmentsController.testPost - Body: " + body);
        Map<String, String> response = new HashMap<>();
        response.put("message", "POST test successful");
        response.put("timestamp", java.time.LocalDateTime.now().toString());
        return response;
    }
} 