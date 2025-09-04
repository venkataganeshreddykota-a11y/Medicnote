package com.example.MedicNote.repository;

import com.example.MedicNote.entity.Prescriptions;
import org.springframework.data.jpa.repository.JpaRepository;
 
public interface PrescriptionsRepository extends JpaRepository<Prescriptions, String> {
    // Custom query methods if needed
} 