package com.example.MedicNote.service;

import com.example.MedicNote.entity.Appointments;
import com.example.MedicNote.entity.AvailableSlots;
import com.example.MedicNote.repository.AppointmentsRepository;
import com.example.MedicNote.repository.AvailableSlotsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.access.AccessDeniedException;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentsService {
    @Autowired
    private AppointmentsRepository appointmentsRepository;
    
    @Autowired
    private AvailableSlotsRepository availableSlotsRepository;

    public List<Appointments> getAllAppointments() {
        // Get current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = authentication.getName();
        
        // For doctors, return only their own appointments
        if (authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_DOCTOR"))) {
            return appointmentsRepository.findByDoctorUserEmail(currentUserId);
        }
        
        // For admin and other roles, return all appointments
        return appointmentsRepository.findAll();
    }

    public Optional<Appointments> getAppointmentById(String id) {
        return appointmentsRepository.findById(id);
    }

    public Appointments saveAppointment(Appointments appointment) {
        // Get current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = authentication.getName();
        
        System.out.println("[DEBUG] AppointmentsService.saveAppointment - currentUserId: " + currentUserId);
        System.out.println("[DEBUG] AppointmentsService.saveAppointment - appointment doctor: " + appointment.getDoctor());
        System.out.println("[DEBUG] AppointmentsService.saveAppointment - appointment doctor email: " + appointment.getDoctor().getUser().getEmail());
        System.out.println("[DEBUG] AppointmentsService.saveAppointment - authentication authorities: " + authentication.getAuthorities());
        
        // For doctors, ensure they can only create/update appointments for themselves
        if (authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_DOCTOR"))) {
            
            System.out.println("[DEBUG] AppointmentsService.saveAppointment - Doctor role detected");
            
            // Check if the doctor is trying to create/update an appointment for themselves
            if (!appointment.getDoctor().getUser().getEmail().equals(currentUserId)) {
                System.out.println("[DEBUG] AppointmentsService.saveAppointment - Access denied: email mismatch");
                System.out.println("[DEBUG] AppointmentsService.saveAppointment - Expected email: " + currentUserId);
                System.out.println("[DEBUG] AppointmentsService.saveAppointment - Actual email: " + appointment.getDoctor().getUser().getEmail());
                throw new AccessDeniedException("Doctors can only create/update appointments for themselves");
            }
            
            System.out.println("[DEBUG] AppointmentsService.saveAppointment - Access granted");
        }
        
        System.out.println("[DEBUG] AppointmentsService.saveAppointment - Saving appointment to database");
        
        // Update the available slot status to 'pending' when an appointment is created
        if (appointment.getAvailableSlotId() != null) {
            Optional<AvailableSlots> availableSlot = availableSlotsRepository.findById(appointment.getAvailableSlotId());
            if (availableSlot.isPresent()) {
                AvailableSlots slot = availableSlot.get();
                slot.setStatus(AvailableSlots.Status.pending);
                availableSlotsRepository.save(slot);
                System.out.println("[DEBUG] AppointmentsService.saveAppointment - Updated available slot status to pending");
            }
        }
        
        return appointmentsRepository.save(appointment);
    }

    public void deleteAppointment(String id) {
        appointmentsRepository.deleteById(id);
    }

    public Appointments acceptAppointment(String id) {
        System.out.println("[DEBUG] AppointmentsService.acceptAppointment - Starting with ID: " + id);
        
        Optional<Appointments> appointmentOpt = appointmentsRepository.findById(id);
        if (!appointmentOpt.isPresent()) {
            throw new RuntimeException("Appointment not found with ID: " + id);
        }
        
        Appointments appointment = appointmentOpt.get();
        System.out.println("[DEBUG] AppointmentsService.acceptAppointment - Found appointment: " + appointment.getId());
        System.out.println("[DEBUG] AppointmentsService.acceptAppointment - Appointment doctor: " + appointment.getDoctor().getUser().getName());
        System.out.println("[DEBUG] AppointmentsService.acceptAppointment - Appointment doctor email: " + appointment.getDoctor().getUser().getEmail());
        
        // Get current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = authentication.getName();
        System.out.println("[DEBUG] AppointmentsService.acceptAppointment - Current user ID: " + currentUserId);
        System.out.println("[DEBUG] AppointmentsService.acceptAppointment - Authentication authorities: " + authentication.getAuthorities());
        
        // Check if the current user is the doctor assigned to this appointment
        if (!appointment.getDoctor().getUser().getEmail().equals(currentUserId)) {
            System.out.println("[DEBUG] AppointmentsService.acceptAppointment - ACCESS DENIED: Email mismatch");
            System.out.println("[DEBUG] AppointmentsService.acceptAppointment - Expected: " + currentUserId);
            System.out.println("[DEBUG] AppointmentsService.acceptAppointment - Actual: " + appointment.getDoctor().getUser().getEmail());
            throw new AccessDeniedException("Only the assigned doctor can accept this appointment");
        }
        
        System.out.println("[DEBUG] AppointmentsService.acceptAppointment - Access granted, proceeding with acceptance");
        
        // Update status to accepted
        appointment.setStatus("accepted");
        
        // Update the available slot status to 'booked' when appointment is accepted
        if (appointment.getAvailableSlotId() != null) {
            Optional<AvailableSlots> availableSlot = availableSlotsRepository.findById(appointment.getAvailableSlotId());
            if (availableSlot.isPresent()) {
                AvailableSlots slot = availableSlot.get();
                slot.setStatus(AvailableSlots.Status.booked);
                availableSlotsRepository.save(slot);
                System.out.println("[DEBUG] AppointmentsService.acceptAppointment - Updated available slot status to booked");
            }
        }
        
        return appointmentsRepository.save(appointment);
    }

    public Appointments rejectAppointment(String id) {
        System.out.println("[DEBUG] AppointmentsService.rejectAppointment - Starting with ID: " + id);
        
        Optional<Appointments> appointmentOpt = appointmentsRepository.findById(id);
        if (!appointmentOpt.isPresent()) {
            throw new RuntimeException("Appointment not found with ID: " + id);
        }
        
        Appointments appointment = appointmentOpt.get();
        System.out.println("[DEBUG] AppointmentsService.rejectAppointment - Found appointment: " + appointment.getId());
        System.out.println("[DEBUG] AppointmentsService.rejectAppointment - Appointment doctor: " + appointment.getDoctor().getUser().getName());
        System.out.println("[DEBUG] AppointmentsService.rejectAppointment - Appointment doctor email: " + appointment.getDoctor().getUser().getEmail());
        
        // Get current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = authentication.getName();
        System.out.println("[DEBUG] AppointmentsService.rejectAppointment - Current user ID: " + currentUserId);
        System.out.println("[DEBUG] AppointmentsService.rejectAppointment - Authentication authorities: " + authentication.getAuthorities());
        
        // Check if the current user is the doctor assigned to this appointment
        if (!appointment.getDoctor().getUser().getEmail().equals(currentUserId)) {
            System.out.println("[DEBUG] AppointmentsService.rejectAppointment - ACCESS DENIED: Email mismatch");
            System.out.println("[DEBUG] AppointmentsService.rejectAppointment - Expected: " + currentUserId);
            System.out.println("[DEBUG] AppointmentsService.rejectAppointment - Actual: " + appointment.getDoctor().getUser().getEmail());
            throw new AccessDeniedException("Only the assigned doctor can reject this appointment");
        }
        
        System.out.println("[DEBUG] AppointmentsService.rejectAppointment - Access granted, proceeding with rejection");
        
        // Update status to rejected
        appointment.setStatus("rejected");
        
        // Update the available slot status back to 'available' when appointment is rejected
        if (appointment.getAvailableSlotId() != null) {
            Optional<AvailableSlots> availableSlot = availableSlotsRepository.findById(appointment.getAvailableSlotId());
            if (availableSlot.isPresent()) {
                AvailableSlots slot = availableSlot.get();
                slot.setStatus(AvailableSlots.Status.available);
                availableSlotsRepository.save(slot);
                System.out.println("[DEBUG] AppointmentsService.rejectAppointment - Updated available slot status to available");
            }
        }
        
        return appointmentsRepository.save(appointment);
    }
} 