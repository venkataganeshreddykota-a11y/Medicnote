package com.example.MedicNote.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Receptionists")
public class Receptionists {
    @Id
    @Column(length = 36)
    private String receptionistId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private Hospitals hospital;

    private java.time.LocalTime shiftStart;
    private java.time.LocalTime shiftEnd;
    private Boolean isAvailable = true;

    // Getters and setters
    public String getReceptionistId() {
        return receptionistId;
    }
    public void setReceptionistId(String receptionistId) {
        this.receptionistId = receptionistId;
    }

    public Users getUser() { return user; }
    public void setUser(Users user) { this.user = user; }

    public Hospitals getHospital() { return hospital; }
    public void setHospital(Hospitals hospital) { this.hospital = hospital; }

    public java.time.LocalTime getShiftStart() { return shiftStart; }
    public void setShiftStart(java.time.LocalTime shiftStart) { this.shiftStart = shiftStart; }

    public java.time.LocalTime getShiftEnd() { return shiftEnd; }
    public void setShiftEnd(java.time.LocalTime shiftEnd) { this.shiftEnd = shiftEnd; }

    public Boolean getIsAvailable() { return isAvailable; }
    public void setIsAvailable(Boolean isAvailable) { this.isAvailable = isAvailable; }
} 