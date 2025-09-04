package com.example.MedicNote.service;

import com.example.MedicNote.entity.Doctors;
import com.example.MedicNote.repository.DoctorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorsService {
    @Autowired
    private DoctorsRepository doctorsRepository;

    public List<Doctors> getAllDoctors() {
        return doctorsRepository.findAll();
    }

    public Optional<Doctors> getDoctorById(String id) {
        return doctorsRepository.findById(id);
    }

    public Doctors saveDoctor(Doctors doctor) {
        return doctorsRepository.save(doctor);
    }

    public void deleteDoctor(String id) {
        doctorsRepository.deleteById(id);
    }

    public List<Doctors> getDoctorsByHospital(String hospitalId) {
        return doctorsRepository.findByHospitalHospitalId(hospitalId);
    }
} 