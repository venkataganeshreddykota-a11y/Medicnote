package com.example.MedicNote.repository;

import com.example.MedicNote.entity.Hospitals;
import org.springframework.data.jpa.repository.JpaRepository;
 
public interface HospitalsRepository extends JpaRepository<Hospitals, String> {
    // Custom query methods if needed
} 