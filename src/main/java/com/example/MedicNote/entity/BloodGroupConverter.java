package com.example.MedicNote.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class BloodGroupConverter implements AttributeConverter<Patients.BloodGroup, String> {
    @Override
    public String convertToDatabaseColumn(Patients.BloodGroup attribute) {
        return attribute == null ? null : attribute.toString();
    }

    @Override
    public Patients.BloodGroup convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        for (Patients.BloodGroup bg : Patients.BloodGroup.values()) {
            if (bg.toString().equals(dbData)) {
                return bg;
            }
        }
        throw new IllegalArgumentException("Unknown blood group: " + dbData);
    }
} 