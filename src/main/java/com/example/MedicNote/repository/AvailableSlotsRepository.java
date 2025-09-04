package com.example.MedicNote.repository;

import com.example.MedicNote.entity.AvailableSlots;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
 
public interface AvailableSlotsRepository extends JpaRepository<AvailableSlots, String> {
    // Find available slots by doctor's user ID
    List<AvailableSlots> findByDoctorUserUserId(String userId);
    
    // Find available slots by doctor's user email
    List<AvailableSlots> findByDoctorUserEmail(String email);
} 