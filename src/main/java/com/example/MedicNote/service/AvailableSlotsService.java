package com.example.MedicNote.service;

import com.example.MedicNote.entity.AvailableSlots;
import com.example.MedicNote.entity.Appointments;
import com.example.MedicNote.entity.Doctors;
import com.example.MedicNote.entity.Slots;
import com.example.MedicNote.repository.AvailableSlotsRepository;
import com.example.MedicNote.repository.AppointmentsRepository;
import com.example.MedicNote.repository.DoctorsRepository;
import com.example.MedicNote.repository.SlotsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.access.AccessDeniedException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class AvailableSlotsService {
    @Autowired
    private AvailableSlotsRepository availableSlotsRepository;
    
    @Autowired
    private AppointmentsRepository appointmentsRepository;
    
    @Autowired
    private SlotsRepository slotsRepository;
    
    @Autowired
    private DoctorsRepository doctorsRepository;

    public List<AvailableSlots> getAllAvailableSlots() {
        // Get current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = authentication.getName();
        
        List<AvailableSlots> slots;
        
        // For doctors, return only their own slots
        if (authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_DOCTOR"))) {
            slots = availableSlotsRepository.findByDoctorUserEmail(currentUserId);
        } else {
            // For admin and other roles, return all slots
            slots = availableSlotsRepository.findAll();
        }
        
        // Update slot statuses based on existing appointments
        for (AvailableSlots slot : slots) {
            // Check for pending appointments
            List<Appointments> pendingAppointments = appointmentsRepository.findByAvailableSlotIdAndStatus(slot.getId(), "pending");
            if (!pendingAppointments.isEmpty()) {
                slot.setStatus(AvailableSlots.Status.pending);
            } else {
                // Check for accepted appointments
                List<Appointments> acceptedAppointments = appointmentsRepository.findByAvailableSlotIdAndStatus(slot.getId(), "accepted");
                if (!acceptedAppointments.isEmpty()) {
                    slot.setStatus(AvailableSlots.Status.booked);
                }
                // If no appointments found, keep the original status
            }
        }
        
        // For doctors, show all their slots (including pending ones)
        // For other roles, filter out slots that have pending appointments
        if (authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_DOCTOR"))) {
            return slots; // Doctors see all their slots
        } else {
            // Filter out slots that have pending appointments for non-doctors
            return slots.stream()
                .filter(slot -> slot.getStatus() != AvailableSlots.Status.pending)
                .collect(Collectors.toList());
        }
    }

    public Optional<AvailableSlots> getAvailableSlotById(String id) {
        // Get current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = authentication.getName();
        
        Optional<AvailableSlots> availableSlot = availableSlotsRepository.findById(id);
        
        // For doctors, ensure they can only access their own slots
        if (authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_DOCTOR"))) {
            
            if (availableSlot.isPresent() && 
                !availableSlot.get().getDoctor().getUser().getEmail().equals(currentUserId)) {
                throw new AccessDeniedException("Doctors can only access their own available slots");
            }
        }
        
        return availableSlot;
    }

    public AvailableSlots saveAvailableSlot(AvailableSlots availableSlot) {
        // Get current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = authentication.getName();
        
        System.out.println("[DEBUG] AvailableSlotsService.saveAvailableSlot - currentUserId: " + currentUserId);
        System.out.println("[DEBUG] AvailableSlotsService.saveAvailableSlot - authentication: " + authentication);
        System.out.println("[DEBUG] AvailableSlotsService.saveAvailableSlot - authorities: " + authentication.getAuthorities());
        System.out.println("[DEBUG] AvailableSlotsService.saveAvailableSlot - availableSlot doctor: " + availableSlot.getDoctor());
        System.out.println("[DEBUG] AvailableSlotsService.saveAvailableSlot - availableSlot doctor ID: " + availableSlot.getDoctor().getDoctorId());
        System.out.println("[DEBUG] AvailableSlotsService.saveAvailableSlot - availableSlot doctor user: " + availableSlot.getDoctor().getUser());
        
        // For doctors, ensure they can only create/update slots for themselves
        if (authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_DOCTOR"))) {
            
            System.out.println("[DEBUG] AvailableSlotsService.saveAvailableSlot - Doctor role detected");
            System.out.println("[DEBUG] AvailableSlotsService.saveAvailableSlot - availableSlot doctor userId: " + availableSlot.getDoctor().getUser().getUserId());
            System.out.println("[DEBUG] AvailableSlotsService.saveAvailableSlot - availableSlot doctor email: " + availableSlot.getDoctor().getUser().getEmail());
            
            // Check if the doctor is trying to create/update a slot for themselves
            // currentUserId is the email, so we need to compare with email
            if (!availableSlot.getDoctor().getUser().getEmail().equals(currentUserId)) {
                System.out.println("[DEBUG] AvailableSlotsService.saveAvailableSlot - Access denied: email mismatch");
                System.out.println("[DEBUG] AvailableSlotsService.saveAvailableSlot - Expected email: " + currentUserId);
                System.out.println("[DEBUG] AvailableSlotsService.saveAvailableSlot - Actual email: " + availableSlot.getDoctor().getUser().getEmail());
                throw new AccessDeniedException("Doctors can only create/update available slots for themselves");
            }
            
            System.out.println("[DEBUG] AvailableSlotsService.saveAvailableSlot - Access granted");
        }
        
        return availableSlotsRepository.save(availableSlot);
    }

    public void deleteAvailableSlot(String id) {
        // Get current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = authentication.getName();
        
        Optional<AvailableSlots> availableSlot = availableSlotsRepository.findById(id);
        
        // For doctors, ensure they can only delete their own slots
        if (authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_DOCTOR"))) {
            
            if (availableSlot.isPresent() && 
                !availableSlot.get().getDoctor().getUser().getEmail().equals(currentUserId)) {
                throw new AccessDeniedException("Doctors can only delete their own available slots");
            }
        }
        
        availableSlotsRepository.deleteById(id);
    }

    // Method to add sample slots for testing
    public List<AvailableSlots> addSampleSlotsForCurrentMonth() {
        // Get current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = authentication.getName();
        
        // Get current doctor
        List<AvailableSlots> existingSlots = availableSlotsRepository.findByDoctorUserEmail(currentUserId);
        if (!existingSlots.isEmpty()) {
            return existingSlots; // Already have slots
        }
        
        // Get all time slots
        List<com.example.MedicNote.entity.Slots> timeSlots = slotsRepository.findAll();
        if (timeSlots.isEmpty()) {
            return new ArrayList<>();
        }
        
        // Get current doctor
        List<Doctors> doctors = doctorsRepository.findByUserEmail(currentUserId);
        if (doctors.isEmpty()) {
            return new ArrayList<>();
        }
        
        Doctors doctor = doctors.get(0);
        List<AvailableSlots> newSlots = new ArrayList<>();
        
        // Add slots for the next few days
        LocalDate today = LocalDate.now();
        for (int i = 0; i < 7; i++) {
            LocalDate date = today.plusDays(i);
            for (int j = 0; j < 3; j++) { // Add 3 slots per day
                if (j < timeSlots.size()) {
                    AvailableSlots slot = new AvailableSlots();
                    slot.setId(UUID.randomUUID().toString());
                    slot.setDoctor(doctor);
                    slot.setSlot(timeSlots.get(j));
                    slot.setDate(date);
                    slot.setStatus(AvailableSlots.Status.available);
                    
                    AvailableSlots savedSlot = availableSlotsRepository.save(slot);
                    newSlots.add(savedSlot);
                }
            }
        }
        
        return newSlots;
    }
} 