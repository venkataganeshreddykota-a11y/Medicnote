CREATE TABLE Users (
    user_id CHAR(36) PRIMARY KEY,
    email VARCHAR(255) UNIQUE,
    password_hash VARCHAR(255),
    role ENUM('admin', 'doctor', 'receptionist', 'patient'),
    name VARCHAR(255),
    phone_number VARCHAR(15)
);

CREATE TABLE Hospitals (
    hospital_id CHAR(36) PRIMARY KEY,
    name VARCHAR(255),
    type VARCHAR(100),
    address TEXT,
    city VARCHAR(100),
    state VARCHAR(100),
    zip_code VARCHAR(20),
    phone_number VARCHAR(15),
    email VARCHAR(255),
    website VARCHAR(255),
    established VARCHAR(50),
    hours VARCHAR(100),
    capacity VARCHAR(100),
    emergency_services TEXT,
    description TEXT
);

CREATE TABLE Doctors (
    doctor_id CHAR(36) PRIMARY KEY,
    user_id CHAR(36),
    specialization VARCHAR(255),
    hospital_id CHAR(36),
    years_of_experience INT,
    license_number VARCHAR(100) UNIQUE,
    biography TEXT,
    qualification VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
    FOREIGN KEY (hospital_id) REFERENCES Hospitals(hospital_id)
);

CREATE TABLE Receptionists (
    receptionist_id CHAR(36) PRIMARY KEY,
    user_id CHAR(36),
    hospital_id CHAR(36),
    shift_start TIME,
    shift_end TIME,
    is_available BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
    FOREIGN KEY (hospital_id) REFERENCES Hospitals(hospital_id)
);

CREATE TABLE Slots (
    slot_id CHAR(36) PRIMARY KEY,
    start_time TIME,
    end_time TIME,
    slot_description VARCHAR(255) DEFAULT NULL
);

CREATE TABLE Patients (
    patient_id CHAR(36) PRIMARY KEY,
    user_id CHAR(36),
    date_of_birth DATE,
    age INT,
    gender ENUM('male', 'female', 'other'),
    medical_history TEXT,
    address TEXT,
    blood_group ENUM('A+', 'A-', 'B+', 'B-', 'AB+', 'AB-', 'O+', 'O-'),
    emergency_contact_person VARCHAR(255),
    emergency_contact_person_contact_number VARCHAR(15),
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);

CREATE TABLE Available_Slots (
    id CHAR(36) PRIMARY KEY,
    doctor_id CHAR(36) NOT NULL,
    slot_id CHAR(36) NOT NULL,
    date DATE NOT NULL,
    status VARCHAR(20) DEFAULT 'available',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (doctor_id) REFERENCES Doctors(doctor_id),
    FOREIGN KEY (slot_id) REFERENCES Slots(slot_id),
    CHECK (status IN ('available', 'booked', 'cancelled', 'pending'))
);

CREATE TABLE Appointments (
    id CHAR(36) PRIMARY KEY,
    patient_id CHAR(36) NOT NULL,
    doctor_id CHAR(36) NOT NULL,
    available_slot_id CHAR(36) NOT NULL,
    type VARCHAR(50) NOT NULL,
    department VARCHAR(50),
    status VARCHAR(50) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES Patients(patient_id),
    FOREIGN KEY (doctor_id) REFERENCES Doctors(doctor_id),
    FOREIGN KEY (available_slot_id) REFERENCES Available_Slots(id)
);



CREATE TABLE Prescriptions (
    prescription_id CHAR(36) PRIMARY KEY,
    appointment_id CHAR(36),
    medications TEXT,
    dosage TEXT,
    instructions TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    secure_link VARCHAR(255),
    diagnosis TEXT,
    status ENUM('active', 'completed', 'cancelled') DEFAULT 'active',
    hospital_id CHAR(36),
    FOREIGN KEY (appointment_id) REFERENCES Appointments(id),
    FOREIGN KEY (hospital_id) REFERENCES Hospitals(hospital_id)
);


INSERT INTO Users (user_id, email, password_hash, role, name, phone_number)
VALUES 
  ('f6611a94-7604-11f0-8d4a-10f60a89d15d', 'admin@example.com', '{bcrypt}$2b$12$CH0.nD6sxoq7Y.hXK0E00Oy.O7FKbvMadqewXjX9FiR7..MfprRuu', 'admin', 'Admin User', '1234567890'),
  ('f66120e2-7604-11f0-8d4a-10f60a89d15d', 'doctor@example.com', '{bcrypt}$2b$12$CH0.nD6sxoq7Y.hXK0E00Oy.O7FKbvMadqewXjX9FiR7..MfprRuu', 'doctor', 'Dr. John Doe', '1234567891'),
  ('f66126d0-7604-11f0-8d4a-10f60a89d15d', 'receptionist@example.com', '{bcrypt}$2b$12$CH0.nD6sxoq7Y.hXK0E00Oy.O7FKbvMadqewXjX9FiR7..MfprRuu', 'receptionist', 'Jane Smith', '1234567892'),
  ('f66133ae-7604-11f0-8d4a-10f60a89d15d', 'patient@example.com', '{bcrypt}$2b$12$CH0.nD6sxoq7Y.hXK0E00Oy.O7FKbvMadqewXjX9FiR7..MfprRuu', 'patient', 'Alice Johnson', '1234567893');


INSERT INTO Hospitals (hospital_id, name, type, address, city, state, zip_code, phone_number, email, website, established, hours, capacity, emergency_services, description)
VALUES 
  (UUID(), 'City Hospital', 'General', '123 Main St, City', 'City', 'State', '12345', '1234567890', 'contact@cityhospital.com', 'www.cityhospital.com', '2000', 'Mon-Fri: 8AM-6PM', '1000', 'Emergency care, Trauma center', 'A comprehensive medical facility.'),
  (UUID(), 'HealthCare Center', 'Specialized', '456 Oak St, Town', 'Town', 'State', '67890', '1234567891', 'contact@healthcarecenter.com', 'www.healthcarecenter.com', '2010', 'Mon-Sat: 9AM-5PM', '500', 'Neurology, Cardiology', 'A specialized healthcare center.');


INSERT INTO Doctors (doctor_id, user_id, specialization, hospital_id, years_of_experience, license_number, biography, qualification)
VALUES 
  ('f6639768-7604-11f0-8d4a-10f60a89d15d', (SELECT user_id FROM Users WHERE email = 'doctor@example.com'), 'Cardiologist', 
   (SELECT hospital_id FROM Hospitals WHERE name = 'City Hospital'), 10, 'LIC12345', 'Experienced cardiologist specializing in heart diseases.', 'MD, Cardiology');


INSERT INTO Receptionists (receptionist_id, user_id, hospital_id, shift_start, shift_end, is_available)
VALUES 
  (UUID(), (SELECT user_id FROM Users WHERE email = 'receptionist@example.com'), 
   (SELECT hospital_id FROM Hospitals WHERE name = 'City Hospital'), '08:00:00', '16:00:00', TRUE);


INSERT INTO Slots (slot_id, start_time, end_time, slot_description)
VALUES
  (UUID(), '00:00:00', '00:30:00', '00:00 - 00:30'),
  (UUID(), '00:30:00', '01:00:00', '00:30 - 01:00'),
  (UUID(), '01:00:00', '01:30:00', '01:00 - 01:30'),
  (UUID(), '01:30:00', '02:00:00', '01:30 - 02:00'),
  (UUID(), '02:00:00', '02:30:00', '02:00 - 02:30'),
  (UUID(), '02:30:00', '03:00:00', '02:30 - 03:00'),
  (UUID(), '03:00:00', '03:30:00', '03:00 - 03:30'),
  (UUID(), '03:30:00', '04:00:00', '03:30 - 04:00'),
  (UUID(), '04:00:00', '04:30:00', '04:00 - 04:30'),
  (UUID(), '04:30:00', '05:00:00', '04:30 - 05:00'),
  (UUID(), '05:00:00', '05:30:00', '05:00 - 05:30'),
  (UUID(), '05:30:00', '06:00:00', '05:30 - 06:00'),
  (UUID(), '06:00:00', '06:30:00', '06:00 - 06:30'),
  (UUID(), '06:30:00', '07:00:00', '06:30 - 07:00'),
  (UUID(), '07:00:00', '07:30:00', '07:00 - 07:30'),
  (UUID(), '07:30:00', '08:00:00', '07:30 - 08:00'),
  (UUID(), '08:00:00', '08:30:00', '08:00 - 08:30'),
  (UUID(), '08:30:00', '09:00:00', '08:30 - 09:00'),
  (UUID(), '09:00:00', '09:30:00', '09:00 - 09:30'),
  (UUID(), '09:30:00', '10:00:00', '09:30 - 10:00'),
  (UUID(), '10:00:00', '10:30:00', '10:00 - 10:30'),
  (UUID(), '10:30:00', '11:00:00', '10:30 - 11:00'),
  (UUID(), '11:00:00', '11:30:00', '11:00 - 11:30'),
  (UUID(), '11:30:00', '12:00:00', '11:30 - 12:00'),
  (UUID(), '12:00:00', '12:30:00', '12:00 - 12:30'),
  (UUID(), '12:30:00', '13:00:00', '12:30 - 13:00'),
  (UUID(), '13:00:00', '13:30:00', '13:00 - 13:30'),
  (UUID(), '13:30:00', '14:00:00', '13:30 - 14:00'),
  (UUID(), '14:00:00', '14:30:00', '14:00 - 14:30'),
  (UUID(), '14:30:00', '15:00:00', '14:30 - 15:00'),
  (UUID(), '15:00:00', '15:30:00', '15:00 - 15:30'),
  (UUID(), '15:30:00', '16:00:00', '15:30 - 16:00'),
  (UUID(), '16:00:00', '16:30:00', '16:00 - 16:30'),
  (UUID(), '16:30:00', '17:00:00', '16:30 - 17:00'),
  (UUID(), '17:00:00', '17:30:00', '17:00 - 17:30'),
  (UUID(), '17:30:00', '18:00:00', '17:30 - 18:00'),
  (UUID(), '18:00:00', '18:30:00', '18:00 - 18:30'),
  (UUID(), '18:30:00', '19:00:00', '18:30 - 19:00'),
  (UUID(), '19:00:00', '19:30:00', '19:00 - 19:30'),
  (UUID(), '19:30:00', '20:00:00', '19:30 - 20:00'),
  (UUID(), '20:00:00', '20:30:00', '20:00 - 20:30'),
  (UUID(), '20:30:00', '21:00:00', '20:30 - 21:00'),
  (UUID(), '21:00:00', '21:30:00', '21:00 - 21:30'),
  (UUID(), '21:30:00', '22:00:00', '21:30 - 22:00'),
  (UUID(), '22:00:00', '22:30:00', '22:00 - 22:30'),
  (UUID(), '22:30:00', '23:00:00', '22:30 - 23:00'),
  (UUID(), '23:00:00', '23:30:00', '23:00 - 23:30'),
  (UUID(), '23:30:00', '00:00:00', '23:30 - 00:00');



INSERT INTO Available_Slots (id, slot_id, doctor_id, date, status)
VALUES 
  ('ae1e6f70-35b0-45d4-8a4a-384f0989176a', (SELECT slot_id FROM Slots LIMIT 1), (SELECT doctor_id FROM Doctors WHERE specialization = 'Cardiologist'), 
   '2025-07-16', 'available'),
  ('f664eb81-7604-11f0-8d4a-10f60a89d15d', (SELECT slot_id FROM Slots LIMIT 1 OFFSET 1), (SELECT doctor_id FROM Doctors WHERE specialization = 'Cardiologist'), 
   '2025-07-17', 'available');



INSERT INTO Patients (patient_id, user_id, date_of_birth, age, gender, medical_history, address, blood_group, emergency_contact_person, emergency_contact_person_contact_number)
VALUES
  ('f6667f98-7604-11f0-8d4a-10f60a89d15d', (SELECT user_id FROM Users WHERE email = 'patient@example.com'), '1990-05-20', 35, 'female', 'No significant history', '789 Pine St', 'A+', 'Bob Johnson', '1234567894');


INSERT INTO Appointments (id, patient_id, available_slot_id, doctor_id, type, department, status, description)
VALUES
  (UUID(), (SELECT patient_id FROM Patients WHERE user_id = (SELECT user_id FROM Users WHERE email = 'patient@example.com')),
   (SELECT id FROM Available_Slots WHERE date = '2025-07-16' AND status = 'available' LIMIT 1),
   (SELECT doctor_id FROM Doctors WHERE specialization = 'Cardiologist'),
   'Consultation', 'Cardiology', 'pending', 'Regular checkup appointment');



INSERT INTO Prescriptions (prescription_id, appointment_id, medications, dosage, instructions, secure_link, diagnosis, status, hospital_id)
VALUES
  (UUID(), (SELECT id FROM Appointments WHERE patient_id = (SELECT patient_id FROM Patients WHERE user_id = (SELECT user_id FROM Users WHERE email = 'patient@example.com')) LIMIT 1),
   'Aspirin, Metoprolol', 'Aspirin 81mg daily, Metoprolol 50mg twice daily', 
   'Take with food, avoid grapefruit', 'https://securelink.com/prescription/12345', 'Hypertension', 'active', (SELECT hospital_id FROM Hospitals WHERE name = 'City Hospital'));
