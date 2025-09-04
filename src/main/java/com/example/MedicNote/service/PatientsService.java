package com.example.MedicNote.service;

import com.example.MedicNote.entity.Patients;
import com.example.MedicNote.repository.PatientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientsService {
    @Autowired
    private PatientsRepository patientsRepository;

    public List<Patients> getAllPatients() {
        return patientsRepository.findAll();
    }

    public Optional<Patients> getPatientById(String id) {
        return patientsRepository.findById(id);
    }

    public Patients savePatient(Patients patient) {
        return patientsRepository.save(patient);
    }

    public void deletePatient(String id) {
        patientsRepository.deleteById(id);
    }

    public List<Patients> getPatientsByHospital(String hospitalId) {
        return patientsRepository.findPatientsByHospitalId(hospitalId);
    }

    // Alternative method using appointments repository if the above doesn't work
    public List<Patients> getPatientsByHospitalAlternative(String hospitalId) {
        // This would require injecting AppointmentsRepository
        // For now, return all patients as fallback
        return getAllPatients();
    }
} 