package com.example.BloodBankManagementSystem.repositories;

import com.example.BloodBankManagementSystem.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment,Integer> {

    public List<Appointment> findByBankId(Integer bankId);
}
