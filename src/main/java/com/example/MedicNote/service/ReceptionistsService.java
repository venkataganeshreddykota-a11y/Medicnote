package com.example.MedicNote.service;

import com.example.MedicNote.entity.Receptionists;
import com.example.MedicNote.repository.ReceptionistsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReceptionistsService {
    @Autowired
    private ReceptionistsRepository receptionistsRepository;

    public List<Receptionists> getAllReceptionists() {
        return receptionistsRepository.findAll();
    }

    public Optional<Receptionists> getReceptionistById(String id) {
        return receptionistsRepository.findById(id);
    }

    public Receptionists saveReceptionist(Receptionists receptionist) {
        return receptionistsRepository.save(receptionist);
    }

    public void deleteReceptionist(String id) {
        receptionistsRepository.deleteById(id);
    }
} 