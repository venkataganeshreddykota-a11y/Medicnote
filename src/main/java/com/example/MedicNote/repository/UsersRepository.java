package com.example.MedicNote.repository;

import com.example.MedicNote.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, String> {
    // Custom query methods if needed
} 