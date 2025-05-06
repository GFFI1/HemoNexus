-- Create the database
CREATE DATABASE IF NOT EXISTS hemonexus CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE hemonexus;

-- Roles table
CREATE TABLE roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(20) NOT NULL
);

-- Users table
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(120) NOT NULL,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    phone_number VARCHAR(20),
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- User roles mapping table
CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

-- Donors table
CREATE TABLE donors (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone_number VARCHAR(20) NOT NULL,
    date_of_birth DATE NOT NULL,
    gender VARCHAR(10) NOT NULL,
    blood_type VARCHAR(5) NOT NULL,
    weight DOUBLE,
    address TEXT,
    city VARCHAR(50),
    state VARCHAR(50),
    zip_code VARCHAR(20),
    country VARCHAR(50),
    is_eligible BOOLEAN DEFAULT TRUE,
    medical_notes TEXT,
    last_donation_date DATE,
    total_donations INT DEFAULT 0,
    user_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Blood banks table
CREATE TABLE blood_banks (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    address TEXT NOT NULL,
    city VARCHAR(50),
    state VARCHAR(50),
    zip_code VARCHAR(20),
    country VARCHAR(50),
    phone_number VARCHAR(20) NOT NULL,
    email VARCHAR(100) NOT NULL,
    website VARCHAR(100),
    license_number VARCHAR(50),
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Donations table
CREATE TABLE donations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    donor_id BIGINT NOT NULL,
    blood_bank_id BIGINT NOT NULL,
    donation_date TIMESTAMP NOT NULL,
    quantity DOUBLE NOT NULL,
    blood_type VARCHAR(5) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    hemoglobin_level VARCHAR(10),
    pulse_rate VARCHAR(10),
    blood_pressure VARCHAR(20),
    body_temperature VARCHAR(10),
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (donor_id) REFERENCES donors(id),
    FOREIGN KEY (blood_bank_id) REFERENCES blood_banks(id)
);

-- Blood inventory table
CREATE TABLE blood_inventory (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    blood_bank_id BIGINT NOT NULL,
    blood_type VARCHAR(5) NOT NULL,
    quantity DOUBLE NOT NULL,
    expiration_date TIMESTAMP NOT NULL,
    donation_id BIGINT,
    storage_location VARCHAR(50),
    batch_number VARCHAR(50),
    is_available BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (blood_bank_id) REFERENCES blood_banks(id),
    FOREIGN KEY (donation_id) REFERENCES donations(id)
);

-- Blood requests table
CREATE TABLE blood_requests (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    blood_bank_id BIGINT NOT NULL,
    patient_name VARCHAR(100) NOT NULL,
    patient_id VARCHAR(50),
    patient_age INT,
    patient_gender VARCHAR(10),
    blood_type VARCHAR(5) NOT NULL,
    quantity_requested DOUBLE NOT NULL,
    quantity_fulfilled DOUBLE DEFAULT 0,
    required_by TIMESTAMP NOT NULL,
    requester_name VARCHAR(100) NOT NULL,
    requester_contact VARCHAR(100),
    hospital_name VARCHAR(100),
    purpose TEXT,
    urgency_level VARCHAR(20),
    notes TEXT,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (blood_bank_id) REFERENCES blood_banks(id)
);

-- Donor eligibility checks table
CREATE TABLE donor_eligibility_checks (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    donor_id BIGINT NOT NULL,
    check_date TIMESTAMP NOT NULL,
    is_eligible BOOLEAN NOT NULL,
    hemoglobin_level VARCHAR(10),
    blood_pressure VARCHAR(20),
    pulse VARCHAR(10),
    weight VARCHAR(10),
    temperature VARCHAR(10),
    has_recent_illness BOOLEAN DEFAULT FALSE,
    has_recent_surgery BOOLEAN DEFAULT FALSE,
    has_tattoo BOOLEAN DEFAULT FALSE,
    next_eligible_date TIMESTAMP,
    notes TEXT,
    checked_by VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (donor_id) REFERENCES donors(id)
);

-- Insert default roles
INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_MODERATOR');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');
INSERT INTO roles (name) VALUES ('ROLE_BLOOD_BANK_ADMIN');