package com.example.MedicNote.repository;

import com.example.MedicNote.entity.Slots;
import org.springframework.data.jpa.repository.JpaRepository;
 
public interface SlotsRepository extends JpaRepository<Slots, String> {
    // Custom query methods if needed
} 