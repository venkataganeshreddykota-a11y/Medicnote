package com.example.MedicNote.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Prescriptions")
public class Prescriptions {
    @Id
    @Column(length = 36)
    private String prescriptionId;

    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private Appointments appointment;

    private String medications;
    private String dosage;
    private String instructions;

    private LocalDateTime createdAt;
    private String secureLink;
    private String diagnosis;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private Hospitals hospital;

    public enum Status {
        active, completed, cancelled
    }

    // Getters and setters
    public String getPrescriptionId() {
        return prescriptionId;
    }
    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public Appointments getAppointment() { return appointment; }
    public void setAppointment(Appointments appointment) { this.appointment = appointment; }

    public String getMedications() { return medications; }
    public void setMedications(String medications) { this.medications = medications; }

    public String getDosage() { return dosage; }
    public void setDosage(String dosage) { this.dosage = dosage; }

    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String getSecureLink() { return secureLink; }
    public void setSecureLink(String secureLink) { this.secureLink = secureLink; }

    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public Hospitals getHospital() { return hospital; }
    public void setHospital(Hospitals hospital) { this.hospital = hospital; }
} 