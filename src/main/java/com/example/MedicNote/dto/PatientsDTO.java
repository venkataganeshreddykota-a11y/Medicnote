package com.example.MedicNote.dto;

import java.time.LocalDate;

public class PatientsDTO {
    private String patientId;
    private String userId;
    private LocalDate dateOfBirth;
    private Integer age;
    private String gender;
    private String medicalHistory;
    private String address;
    private String bloodGroup;
    private String emergencyContactPerson;
    private String emergencyContactPersonContactNumber;

    // Getters and setters
    public String getPatientId() {
        return patientId;
    }
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }
    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }
    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getEmergencyContactPerson() {
        return emergencyContactPerson;
    }
    public void setEmergencyContactPerson(String emergencyContactPerson) {
        this.emergencyContactPerson = emergencyContactPerson;
    }

    public String getEmergencyContactPersonContactNumber() {
        return emergencyContactPersonContactNumber;
    }
    public void setEmergencyContactPersonContactNumber(String emergencyContactPersonContactNumber) {
        this.emergencyContactPersonContactNumber = emergencyContactPersonContactNumber;
    }
} 