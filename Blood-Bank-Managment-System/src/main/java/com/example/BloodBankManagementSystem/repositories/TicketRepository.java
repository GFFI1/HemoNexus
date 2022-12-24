package com.example.BloodBankManagementSystem.repositories;

import com.example.BloodBankManagementSystem.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket,Integer> {

    List<Ticket> findByBankId(Integer bankId);
}
