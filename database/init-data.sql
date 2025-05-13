-- Use the hemonexus database
USE hemonexus;

-- Insert admin user (password: admin123)
INSERT INTO users (username, email, password, first_name, last_name, phone_number, active)
VALUES ('admin', 'admin@hemonexus.com', '$2a$10$NHKirlzGQczTiI5hPGxdJu4Uh5W/UdewdAl0W/WPe1RHUO7cS4FoW', 'Admin', 'User', '123-456-7890', true);

-- Assign admin role to admin user
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id FROM users u, roles r WHERE u.username = 'admin' AND r.name = 'ROLE_ADMIN';

-- Insert a regular user (password: user123)
INSERT INTO users (username, email, password, first_name, last_name, phone_number, active)
VALUES ('user', 'user@hemonexus.com', '$2a$10$HFQRbW6FQFtVmU45XSHnROwvPg7cPjXoD4sfFAltZQPKbKA5e6oPm', 'Regular', 'User', '987-654-3210', true);

-- Assign user role to regular user
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id FROM users u, roles r WHERE u.username = 'user' AND r.name = 'ROLE_USER';

-- Insert blood bank admin (password: bloodbank123)
INSERT INTO users (username, email, password, first_name, last_name, phone_number, active)
VALUES ('bloodbank', 'bloodbank@hemonexus.com', '$2a$10$CvFTu2TFeFcUXdtUP2j7p.9U2CZS7tr/a5xV0VlbUO9PD9S7TXwKe', 'Blood Bank', 'Admin', '555-555-5555', true);

-- Assign blood bank admin role
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id FROM users u, roles r WHERE u.username = 'bloodbank' AND r.name = 'ROLE_BLOOD_BANK_ADMIN';

-- Insert sample blood banks
INSERT INTO blood_banks (name, address, city, state, zip_code, country, phone_number, email, website, license_number, description)
VALUES 
('Central Blood Bank', '123 Main Street', 'New York', 'NY', '10001', 'USA', '212-555-1234', 'contact@centralbloodbank.com', 'www.centralbloodbank.com', 'CBB-2023-001', 'Main blood bank serving the New York metropolitan area.'),
('Lifeline Blood Center', '456 Park Avenue', 'Chicago', 'IL', '60601', 'USA', '312-555-6789', 'info@lifelineblood.org', 'www.lifelineblood.org', 'LBC-2023-002', 'Providing blood services to hospitals across Chicago since 1985.'),
('Red Cross Blood Services', '789 Oak Drive', 'Los Angeles', 'CA', '90001', 'USA', '323-555-4321', 'la@redcrossblood.org', 'www.redcrossblood.org/la', 'RCBS-2023-003', 'Part of the nationwide Red Cross blood services network.');

-- Insert sample donors
INSERT INTO donors (first_name, last_name, email, phone_number, date_of_birth, gender, blood_type, weight, address, city, state, zip_code, country, is_eligible, total_donations)
VALUES 
('John', 'Smith', 'john.smith@example.com', '212-555-9876', '1985-03-15', 'Male', 'O+', 80.5, '123 Broadway', 'New York', 'NY', '10003', 'USA', true, 5),
('Emily', 'Johnson', 'emily.johnson@example.com', '312-555-1234', '1990-07-22', 'Female', 'A+', 65.0, '456 Michigan Ave', 'Chicago', 'IL', '60611', 'USA', true, 3),
('Michael', 'Williams', 'michael.williams@example.com', '323-555-5678', '1978-11-30', 'Male', 'B-', 90.2, '789 Sunset Blvd', 'Los Angeles', 'CA', '90028', 'USA', true, 8),
('Sarah', 'Brown', 'sarah.brown@example.com', '415-555-4321', '1992-05-18', 'Female', 'AB+', 58.7, '101 Market St', 'San Francisco', 'CA', '94105', 'USA', true, 2),
('David', 'Jones', 'david.jones@example.com', '617-555-8765', '1983-09-10', 'Male', 'O-', 75.3, '222 Newbury St', 'Boston', 'MA', '02115', 'USA', false, 4);

-- Insert sample donations
INSERT INTO donations (donor_id, blood_bank_id, donation_date, quantity, blood_type, status, hemoglobin_level, pulse_rate, blood_pressure, body_temperature)
VALUES 
(1, 1, '2023-01-15 10:30:00', 450, 'O+', 'COMPLETED', '14.2', '72', '120/80', '98.6'),
(2, 2, '2023-02-20 13:45:00', 500, 'A+', 'COMPLETED', '13.5', '68', '118/76', '98.4'),
(3, 3, '2023-03-10 09:15:00', 450, 'B-', 'COMPLETED', '15.0', '70', '122/82', '98.8'),
(1, 1, '2023-04-18 11:00:00', 450, 'O+', 'COMPLETED', '14.0', '74', '124/78', '98.5'),
(3, 2, '2023-05-05 14:30:00', 500, 'B-', 'COMPLETED', '14.8', '72', '120/82', '98.7'),
(4, 3, '2023-05-12 10:45:00', 450, 'AB+', 'COMPLETED', '13.2', '76', '126/84', '98.9'),
(1, 2, '2023-05-30 13:00:00', 450, 'O+', 'PENDING', NULL, NULL, NULL, NULL);

-- Insert sample blood inventory
INSERT INTO blood_inventory (blood_bank_id, blood_type, quantity, expiration_date, donation_id, storage_location, batch_number, is_available)
VALUES 
(1, 'O+', 450, '2023-07-15 10:30:00', 1, 'Shelf A1', 'BN-001-2023', true),
(2, 'A+', 500, '2023-08-20 13:45:00', 2, 'Refrigerator R2', 'BN-002-2023', true),
(3, 'B-', 450, '2023-09-10 09:15:00', 3, 'Shelf B3', 'BN-003-2023', true),
(1, 'O+', 450, '2023-10-18 11:00:00', 4, 'Refrigerator R1', 'BN-004-2023', true),
(2, 'B-', 500, '2023-11-05 14:30:00', 5, 'Shelf A2', 'BN-005-2023', true),
(3, 'AB+', 450, '2023-11-12 10:45:00', 6, 'Refrigerator R3', 'BN-006-2023', true),
(1, 'A-', 450, '2023-09-30 00:00:00', NULL, 'Shelf A3', 'BN-007-2023', true),
(2, 'O-', 500, '2023-10-31 00:00:00', NULL, 'Refrigerator R1', 'BN-008-2023', true);

-- Insert sample blood requests
INSERT INTO blood_requests (blood_bank_id, patient_name, patient_id, patient_age, patient_gender, blood_type, quantity_requested, quantity_fulfilled, required_by, requester_name, requester_contact, hospital_name, purpose, urgency_level, status)
VALUES 
(1, 'James Wilson', 'P-12345', 45, 'Male', 'O+', 450, 450, '2023-06-10 08:00:00', 'Dr. Robert Chen', '212-555-1111', 'City General Hospital', 'Surgery', 'HIGH', 'FULFILLED'),
(2, 'Maria Garcia', 'P-23456', 35, 'Female', 'A+', 900, 500, '2023-06-12 14:00:00', 'Dr. Linda Kim', '312-555-2222', 'Memorial Hospital', 'Emergency', 'CRITICAL', 'PARTIALLY_FULFILLED'),
(3, 'Thomas Moore', 'P-34567', 62, 'Male', 'B-', 450, 0, '2023-06-20 10:00:00', 'Dr. William Johnson', '323-555-3333', 'Pacific Medical Center', 'Transfusion', 'MEDIUM', 'PENDING'),
(1, 'Jennifer Lee', 'P-45678', 28, 'Female', 'AB+', 450, 0, '2023-06-25 16:00:00', 'Dr. Susan Taylor', '212-555-4444', 'University Hospital', 'Accident Victim', 'HIGH', 'PENDING');

-- Insert sample donor eligibility checks
INSERT INTO donor_eligibility_checks (donor_id, check_date, is_eligible, hemoglobin_level, blood_pressure, pulse, weight, temperature, has_recent_illness, next_eligible_date, notes, checked_by)
VALUES 
(1, '2023-01-15 09:30:00', true, '14.2', '120/80', '72', '80.5', '98.6', false, '2023-04-15 09:30:00', 'All parameters normal', 'Nurse Johnson'),
(2, '2023-02-20 12:45:00', true, '13.5', '118/76', '68', '65.0', '98.4', false, '2023-05-20 12:45:00', 'All parameters normal', 'Nurse Smith'),
(3, '2023-03-10 08:15:00', true, '15.0', '122/82', '70', '90.2', '98.8', false, '2023-06-10 08:15:00', 'All parameters normal', 'Nurse Williams'),
(4, '2023-05-12 09:45:00', true, '13.2', '126/84', '76', '58.7', '98.9', false, '2023-08-12 09:45:00', 'All parameters normal', 'Nurse Davis'),
(5, '2023-04-05 10:00:00', false, '11.8', '130/90', '82', '75.3', '99.1', true, '2023-07-05 10:00:00', 'Low hemoglobin level and recent cold symptoms', 'Nurse Johnson');