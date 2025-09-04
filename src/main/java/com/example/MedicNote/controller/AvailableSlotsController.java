package com.example.MedicNote.controller;

import com.example.MedicNote.dto.AvailableSlotsDTO;
import com.example.MedicNote.entity.AvailableSlots;
import com.example.MedicNote.entity.Doctors;
import com.example.MedicNote.entity.Slots;
import com.example.MedicNote.service.AvailableSlotsService;
import com.example.MedicNote.service.DoctorsService;
import com.example.MedicNote.service.SlotsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/available-slots")
@Tag(name = "Available Slots")
public class AvailableSlotsController {
    @Autowired
    private AvailableSlotsService availableSlotsService;
    
    @Autowired
    private DoctorsService doctorsService;
    
    @Autowired
    private SlotsService slotsService;

    @GetMapping
    public List<AvailableSlots> getAllAvailableSlots() {
        return availableSlotsService.getAllAvailableSlots();
    }

    @GetMapping("/{id}")
    public Optional<AvailableSlots> getAvailableSlotById(@PathVariable String id) {
        return availableSlotsService.getAvailableSlotById(id);
    }

    @PostMapping
    public AvailableSlots createAvailableSlot(@RequestBody AvailableSlotsDTO dto) {
        // Convert DTO to entity
        AvailableSlots availableSlot = new AvailableSlots();
        availableSlot.setId(UUID.randomUUID().toString());
        
        // Set doctor
        Optional<Doctors> doctor = doctorsService.getDoctorById(dto.getDoctorId());
        if (doctor.isPresent()) {
            availableSlot.setDoctor(doctor.get());
        } else {
            throw new RuntimeException("Doctor not found with ID: " + dto.getDoctorId());
        }
        
        // Set slot
        Optional<Slots> slot = slotsService.getSlotById(dto.getSlotId());
        if (slot.isPresent()) {
            availableSlot.setSlot(slot.get());
        } else {
            throw new RuntimeException("Slot not found with ID: " + dto.getSlotId());
        }
        
        // Set other fields
        availableSlot.setDate(LocalDate.parse(dto.getDate()));
        availableSlot.setStatus(AvailableSlots.Status.valueOf(dto.getStatus()));
        
        return availableSlotsService.saveAvailableSlot(availableSlot);
    }

    @PutMapping("/{id}")
    public AvailableSlots updateAvailableSlot(@PathVariable String id, @RequestBody AvailableSlotsDTO dto) {
        // Get existing available slot
        Optional<AvailableSlots> existingSlot = availableSlotsService.getAvailableSlotById(id);
        if (!existingSlot.isPresent()) {
            throw new RuntimeException("Available slot not found with ID: " + id);
        }
        
        AvailableSlots availableSlot = existingSlot.get();
        availableSlot.setId(id);
        
        // Set doctor
        Optional<Doctors> doctor = doctorsService.getDoctorById(dto.getDoctorId());
        if (doctor.isPresent()) {
            availableSlot.setDoctor(doctor.get());
        } else {
            throw new RuntimeException("Doctor not found with ID: " + dto.getDoctorId());
        }
        
        // Set slot
        Optional<Slots> slot = slotsService.getSlotById(dto.getSlotId());
        if (slot.isPresent()) {
            availableSlot.setSlot(slot.get());
        } else {
            throw new RuntimeException("Slot not found with ID: " + dto.getSlotId());
        }
        
        // Set other fields
        availableSlot.setDate(LocalDate.parse(dto.getDate()));
        availableSlot.setStatus(AvailableSlots.Status.valueOf(dto.getStatus()));
        
        return availableSlotsService.saveAvailableSlot(availableSlot);
    }

    @DeleteMapping("/{id}")
    public void deleteAvailableSlot(@PathVariable String id) {
        availableSlotsService.deleteAvailableSlot(id);
    }

    @PostMapping("/sample")
    public List<AvailableSlots> createSampleSlots() {
        return availableSlotsService.addSampleSlotsForCurrentMonth();
    }
} 