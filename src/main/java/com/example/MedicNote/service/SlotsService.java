package com.example.MedicNote.service;

import com.example.MedicNote.entity.Slots;
import com.example.MedicNote.repository.SlotsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SlotsService {
    @Autowired
    private SlotsRepository slotsRepository;

    public List<Slots> getAllSlots() {
        return slotsRepository.findAll();
    }

    public Optional<Slots> getSlotById(String id) {
        return slotsRepository.findById(id);
    }

    public Slots saveSlot(Slots slot) {
        return slotsRepository.save(slot);
    }

    public void deleteSlot(String id) {
        slotsRepository.deleteById(id);
    }
} 