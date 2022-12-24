package com.example.BloodBankManagementSystem.services;

import com.example.BloodBankManagementSystem.payload.TicketDto;

import java.util.List;

public interface TicketService {
    //admin can access it...
    public List<TicketDto> getAllTickets();

    //User and manager can access it
    public TicketDto getTicketById(Integer ticketId);

    //API's corresponding to manager
    //manager can see the list of the tickets that is issued from his bank..
    public List<TicketDto> getAllTicketsIssuedForBank();
    public List<TicketDto> getAllTheValidTickets();
    public void deleteTicket(Integer ticketId);
    public TicketDto approveTicket(Integer ticketId);
}
