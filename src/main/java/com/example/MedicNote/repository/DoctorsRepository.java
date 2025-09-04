package com.example.MedicNote.repository;

import com.example.MedicNote.entity.Doctors;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
 
public interface DoctorsRepository extends JpaRepository<Doctors, String> {
    // Custom query methods if needed
    List<Doctors> findByHospitalHospitalId(String hospitalId);
    
    // Find doctors by user email
    List<Doctors> findByUserEmail(String email);
} 