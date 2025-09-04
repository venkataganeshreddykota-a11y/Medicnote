package com.example.MedicNote.controller;

import com.example.MedicNote.entity.Receptionists;
import com.example.MedicNote.service.ReceptionistsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/receptionists")
@Tag(name = "Receptionists")
public class ReceptionistsController {
    @Autowired
    private ReceptionistsService receptionistsService;

    @GetMapping
    public List<Receptionists> getAllReceptionists() {
        return receptionistsService.getAllReceptionists();
    }

    @GetMapping("/{id}")
    public Optional<Receptionists> getReceptionistById(@PathVariable String id) {
        return receptionistsService.getReceptionistById(id);
    }

    @PostMapping
    public Receptionists createReceptionist(@RequestBody Receptionists receptionist) {
        return receptionistsService.saveReceptionist(receptionist);
    }

    @PutMapping("/{id}")
    public Receptionists updateReceptionist(@PathVariable String id, @RequestBody Receptionists receptionist) {
        receptionist.setReceptionistId(id);
        return receptionistsService.saveReceptionist(receptionist);
    }

    @DeleteMapping("/{id}")
    public void deleteReceptionist(@PathVariable String id) {
        receptionistsService.deleteReceptionist(id);
    }
} 