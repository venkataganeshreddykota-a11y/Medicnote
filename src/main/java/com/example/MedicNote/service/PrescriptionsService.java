package com.example.MedicNote.service;

import com.example.MedicNote.entity.Prescriptions;
import com.example.MedicNote.repository.PrescriptionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrescriptionsService {
    @Autowired
    private PrescriptionsRepository prescriptionsRepository;

    public List<Prescriptions> getAllPrescriptions() {
        return prescriptionsRepository.findAll();
    }

    public Optional<Prescriptions> getPrescriptionById(String id) {
        return prescriptionsRepository.findById(id);
    }

    public Prescriptions savePrescription(Prescriptions prescription) {
        return prescriptionsRepository.save(prescription);
    }

    public void deletePrescription(String id) {
        prescriptionsRepository.deleteById(id);
    }
} 