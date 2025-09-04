package com.example.MedicNote.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Available_Slots")
public class AvailableSlots {
    @Id
    @Column(length = 36)
    private String id;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctors doctor;

    @ManyToOne
    @JoinColumn(name = "slot_id")
    private Slots slot;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Status status = Status.available;



    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public enum Status {
        available, booked, cancelled, pending
    }

    // Getters and setters
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public Doctors getDoctor() { return doctor; }
    public void setDoctor(Doctors doctor) { this.doctor = doctor; }

    public Slots getSlot() { return slot; }
    public void setSlot(Slots slot) { this.slot = slot; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }



    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
} 