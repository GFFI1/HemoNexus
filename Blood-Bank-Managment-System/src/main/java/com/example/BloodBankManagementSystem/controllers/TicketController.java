package com.example.BloodBankManagementSystem.controllers;

import com.example.BloodBankManagementSystem.payload.TicketDto;
import com.example.BloodBankManagementSystem.pojo.Response;
import com.example.BloodBankManagementSystem.pojo.Status;
import com.example.BloodBankManagementSystem.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/ticket")
public class TicketController {

    @Autowired
    TicketService ticketService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public Response<List<TicketDto>> getAllTickets(){
        List<TicketDto> ticketDtos=ticketService.getAllTickets();
        Response<List<TicketDto>> response=new Response<>();
        response.setBody(ticketDtos);
        response.setStatus(new Status(HttpStatus.OK.value(), true,"Fetched all the tickets"));
        return response;
    }

    @GetMapping(value = "/{ticketId}")
    public Response<TicketDto> getTicketById(@PathVariable("ticketId") Integer ticketId){
        TicketDto ticketDto=ticketService.getTicketById(ticketId);
        Response<TicketDto> response=new Response<>();
        response.setBody(ticketDto);
        response.setStatus(new Status(HttpStatus.OK.value(), true,"Fetched ticket by the Id"));
        return response;
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping(value = "/bank")
    public Response<List<TicketDto>> getAllTicketsIssuedForBank(){
        List<TicketDto> ticketDtos=ticketService.getAllTicketsIssuedForBank();
        Response<List<TicketDto>> response=new Response<>();
        response.setBody(ticketDtos);
        response.setStatus(new Status(HttpStatus.OK.value(), true,"Fetched ticket by the Id"));
        return response;
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping(value = "/bank/valid")
    public Response<List<TicketDto>> getAllTheValidTickets(){
        List<TicketDto> ticketDtos=ticketService.getAllTheValidTickets();
        Response<List<TicketDto>> response=new Response<>();
        response.setBody(ticketDtos);
        response.setStatus(new Status(HttpStatus.OK.value(), true,"Fetched All the valid ticket"));
        return response;
    }

    @PreAuthorize("hasRole('MANAGER')")
    @DeleteMapping(value = "/{ticketId}")
    public Response<String> deleteTicketById(@PathVariable("ticketId") Integer ticketId){
        ticketService.deleteTicket(ticketId);
        Response<String> response=new Response<>();
        response.setBody("Ticket is deleted");
        response.setStatus(new Status(HttpStatus.OK.value(), true,"Deletion is done successfull"));
        return response;
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping(value = "/approve/{ticketId}")
    public Response<TicketDto> approveTicket(@PathVariable("ticketId") Integer ticketId){
        TicketDto ticketDto=ticketService.approveTicket(ticketId);
        Response<TicketDto> response=new Response<>();
        response.setBody(ticketDto);
        response.setStatus(new Status(HttpStatus.OK.value(), true,"Ticket is approved and transactions is created"));
        return response;
    }









}
