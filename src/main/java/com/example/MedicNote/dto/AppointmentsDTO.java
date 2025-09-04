package com.example.MedicNote.dto;

public class AppointmentsDTO {
    private String patientId;
    private String doctorId;
    private String availableSlotId;
    private String type;
    private String department;
    private String status;
    private String description;

    // Getters and setters
    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getAvailableSlotId() {
        return availableSlotId;
    }

    public void setAvailableSlotId(String availableSlotId) {
        this.availableSlotId = availableSlotId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
} 