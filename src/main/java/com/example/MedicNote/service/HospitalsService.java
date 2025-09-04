package com.example.MedicNote.service;

import com.example.MedicNote.entity.Hospitals;
import com.example.MedicNote.repository.HospitalsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HospitalsService {
    @Autowired
    private HospitalsRepository hospitalsRepository;

    public List<Hospitals> getAllHospitals() {
        return hospitalsRepository.findAll();
    }

    public Optional<Hospitals> getHospitalById(String id) {
        return hospitalsRepository.findById(id);
    }

    public Hospitals saveHospital(Hospitals hospital) {
        return hospitalsRepository.save(hospital);
    }

    public void deleteHospital(String id) {
        hospitalsRepository.deleteById(id);
    }
} 