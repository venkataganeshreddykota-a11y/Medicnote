package com.example.MedicNote.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Slots")
public class Slots {
    @Id
    @Column(length = 36)
    private String slotId;

    private java.time.LocalTime startTime;
    private java.time.LocalTime endTime;
    private String slotDescription;

    // Getters and setters
    public String getSlotId() {
        return slotId;
    }
    public void setSlotId(String slotId) {
        this.slotId = slotId;
    }

    public java.time.LocalTime getStartTime() {
        return startTime;
    }
    public void setStartTime(java.time.LocalTime startTime) {
        this.startTime = startTime;
    }

    public java.time.LocalTime getEndTime() {
        return endTime;
    }
    public void setEndTime(java.time.LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getSlotDescription() {
        return slotDescription;
    }
    public void setSlotDescription(String slotDescription) {
        this.slotDescription = slotDescription;
    }
} 