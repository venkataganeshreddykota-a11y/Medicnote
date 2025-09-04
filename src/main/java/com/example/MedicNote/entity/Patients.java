package com.example.MedicNote.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Patients")
public class Patients {
    @Id
    @Column(length = 36)
    private String patientId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    private LocalDate dateOfBirth;
    private Integer age;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Gender gender;

    private String medicalHistory;
    private String address;

    @Convert(converter = BloodGroupConverter.class)
    @Column(length = 3)
    private BloodGroup bloodGroup;

    private String emergencyContactPerson;
    private String emergencyContactPersonContactNumber;

    public enum Gender {
        male, female, other
    }
    public enum BloodGroup {
        A_PLUS("A+"),
        A_MINUS("A-"),
        B_PLUS("B+"),
        B_MINUS("B-"),
        AB_PLUS("AB+"),
        AB_MINUS("AB-"),
        O_PLUS("O+"),
        O_MINUS("O-");

        private final String value;
        BloodGroup(String value) { this.value = value; }
        @Override
        public String toString() { return value; }
    }
    // Getters and setters
    public String getPatientId() {
        return patientId;
    }
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public Users getUser() { return user; }
    public void setUser(Users user) { this.user = user; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }

    public String getMedicalHistory() { return medicalHistory; }
    public void setMedicalHistory(String medicalHistory) { this.medicalHistory = medicalHistory; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public BloodGroup getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(BloodGroup bloodGroup) { this.bloodGroup = bloodGroup; }

    public String getEmergencyContactPerson() { return emergencyContactPerson; }
    public void setEmergencyContactPerson(String emergencyContactPerson) { this.emergencyContactPerson = emergencyContactPerson; }

    public String getEmergencyContactPersonContactNumber() { return emergencyContactPersonContactNumber; }
    public void setEmergencyContactPersonContactNumber(String emergencyContactPersonContactNumber) { this.emergencyContactPersonContactNumber = emergencyContactPersonContactNumber; }
} 