package com.example.MedicNote.dto;

import java.time.LocalDateTime;

public class PrescriptionsDTO {
    private String prescriptionId;
    private String appointmentId;
    private String medications;
    private String dosage;
    private String instructions;
    private LocalDateTime createdAt;
    private String secureLink;
    private String diagnosis;
    private String status;
    private String hospitalId;

    // Getters and setters
    public String getPrescriptionId() {
        return prescriptionId;
    }
    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public String getAppointmentId() {
        return appointmentId;
    }
    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getMedications() {
        return medications;
    }
    public void setMedications(String medications) {
        this.medications = medications;
    }

    public String getDosage() {
        return dosage;
    }
    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getInstructions() {
        return instructions;
    }
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getSecureLink() {
        return secureLink;
    }
    public void setSecureLink(String secureLink) {
        this.secureLink = secureLink;
    }

    public String getDiagnosis() {
        return diagnosis;
    }
    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getHospitalId() {
        return hospitalId;
    }
    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }
} 