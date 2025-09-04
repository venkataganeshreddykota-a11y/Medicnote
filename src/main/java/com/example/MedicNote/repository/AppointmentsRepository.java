package com.example.MedicNote.repository;

import com.example.MedicNote.entity.Appointments;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
 
public interface AppointmentsRepository extends JpaRepository<Appointments, String> {
    // Find appointments by doctor's user email
    List<Appointments> findByDoctorUserEmail(String email);
    
    // Find appointments by available slot ID and status
    List<Appointments> findByAvailableSlotIdAndStatus(String availableSlotId, String status);
    
    // Find appointments by available slot ID
    List<Appointments> findByAvailableSlotId(String availableSlotId);
} 