package com.example.MedicNote.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Doctors")
public class Doctors {
    @Id
    @Column(length = 36)
    private String doctorId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    private String specialization;

    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private Hospitals hospital;

    private int yearsOfExperience;

    @Column(unique = true)
    private String licenseNumber;

    private String biography;
    private String qualification;

    // Getters and setters
    public String getDoctorId() {
        return doctorId;
    }
    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public Users getUser() { return user; }
    public void setUser(Users user) { this.user = user; }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public Hospitals getHospital() { return hospital; }
    public void setHospital(Hospitals hospital) { this.hospital = hospital; }

    public int getYearsOfExperience() { return yearsOfExperience; }
    public void setYearsOfExperience(int yearsOfExperience) { this.yearsOfExperience = yearsOfExperience; }

    public String getLicenseNumber() { return licenseNumber; }
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }

    public String getBiography() { return biography; }
    public void setBiography(String biography) { this.biography = biography; }

    public String getQualification() { return qualification; }
    public void setQualification(String qualification) { this.qualification = qualification; }
} 