package com.example.MedicNote.controller;

import com.example.MedicNote.entity.Slots;
import com.example.MedicNote.service.SlotsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/slots")
@Tag(name = "Slots")
public class SlotsController {
    @Autowired
    private SlotsService slotsService;

    @GetMapping
    public List<Slots> getAllSlots() {
        return slotsService.getAllSlots();
    }

    @GetMapping("/{id}")
    public Optional<Slots> getSlotById(@PathVariable String id) {
        return slotsService.getSlotById(id);
    }

    @PostMapping
    public Slots createSlot(@RequestBody Slots slot) {
        return slotsService.saveSlot(slot);
    }

    @PutMapping("/{id}")
    public Slots updateSlot(@PathVariable String id, @RequestBody Slots slot) {
        slot.setSlotId(id);
        return slotsService.saveSlot(slot);
    }

    @DeleteMapping("/{id}")
    public void deleteSlot(@PathVariable String id) {
        slotsService.deleteSlot(id);
    }
} 