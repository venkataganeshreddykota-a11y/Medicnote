package com.example.MedicNote.repository;

import com.example.MedicNote.entity.Patients;
import com.example.MedicNote.entity.Appointments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface PatientsRepository extends JpaRepository<Patients, String> {
    // Custom query methods if needed
    
    @Query("SELECT DISTINCT a.patient FROM Appointments a " +
           "JOIN a.doctor d " +
           "WHERE d.hospital.hospitalId = :hospitalId")
    List<Patients> findPatientsByHospitalId(@Param("hospitalId") String hospitalId);
    
    // Alternative simpler query for testing
    @Query("SELECT COUNT(a) FROM Appointments a WHERE a.doctor.hospital.hospitalId = :hospitalId")
    Long countAppointmentsByHospital(@Param("hospitalId") String hospitalId);
} 