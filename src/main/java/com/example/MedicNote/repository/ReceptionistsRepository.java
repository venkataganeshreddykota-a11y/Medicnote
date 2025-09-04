package com.example.MedicNote.repository;

import com.example.MedicNote.entity.Receptionists;
import org.springframework.data.jpa.repository.JpaRepository;
 
public interface ReceptionistsRepository extends JpaRepository<Receptionists, String> {
    // Custom query methods if needed
} 