package com.example.BloodBankManagementSystem.services.impl;

import com.example.BloodBankManagementSystem.entities.*;
import com.example.BloodBankManagementSystem.exceptions.NotFoundException;
import com.example.BloodBankManagementSystem.payload.TicketDto;
import com.example.BloodBankManagementSystem.repositories.BloodBankRepository;
import com.example.BloodBankManagementSystem.repositories.TicketRepository;
import com.example.BloodBankManagementSystem.repositories.TransactionRepository;
import com.example.BloodBankManagementSystem.services.TicketService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    BloodBankRepository bloodBankRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public List<TicketDto> getAllTickets() {
        List<Ticket> tickets=ticketRepository.findAll();
        return DtoListfromEntityList(tickets);
    }

    @Override
    public TicketDto getTicketById(Integer ticketId) {
        Ticket ticket=getTicket(ticketId);
        return EntityToDto(ticket);
    }

    @Override
    public List<TicketDto> getAllTicketsIssuedForBank() {
        List<Ticket> tickets=fetchTicketsCorrespondingToBank();
        return DtoListfromEntityList(tickets);
    }

    @Override
    public List<TicketDto> getAllTheValidTickets() {
        List<Ticket> tickets=fetchTicketsCorrespondingToBank();
        //the ticket which are issue today and those which are issued yesterday are valid today
        Calendar calendar=Calendar.getInstance();
        Date today=calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR,1);
        Date tomorrow=calendar.getTime();

        List<Ticket> validTickets=new ArrayList<>();

        tickets.stream().peek((ticket -> {
            if(ticket.getValidDate().compareTo(today) > 0 || ticket.getValidDate().compareTo(tomorrow) > 0){
                validTickets.add(ticket);
            }
        })).collect(Collectors.toList());
        return DtoListfromEntityList(validTickets);
    }

    @Override
    public void deleteTicket(Integer ticketId) {
        Ticket ticket=getTicket(ticketId);
        ticketRepository.delete(ticket);
    }

    @Override
    public TicketDto approveTicket(Integer ticketId) {
        //here the transaction  will be generated and the amount of blood in the bank will vary
        Ticket ticket=getTicket(ticketId);
        BloodBank bloodBank=bloodBankRepository.getById(ticket.getBankId());
        int quantity=ticket.getQuantity();
        BloodType bloodType=ticket.getBloodType();
        List<BloodDetail> bloodDetails=bloodBank.getBloodDetailList();
        if(ticket.getDonor()){
            bloodDetails.stream().map((bloodDetail -> {
                if(bloodDetail.getBloodType()==bloodType){
                    int currentAmount= Math.toIntExact(bloodDetail.getAmount());
                    int upDatedAmount=currentAmount+quantity;
                    bloodDetail.setAmount((long) upDatedAmount);
                }
                return bloodDetail;
            })).collect(Collectors.toList());
        }
        else{
            bloodDetails.stream().map((bloodDetail -> {
                if(bloodDetail.getBloodType()==bloodType){
                    int currentAmount= Math.toIntExact(bloodDetail.getAmount());
                    int upDatedAmount=currentAmount-quantity;
                    bloodDetail.setAmount((long) upDatedAmount);
                }
                return bloodDetail;
            })).collect(Collectors.toList());
        }
        bloodBank.setBloodDetailList(bloodDetails);
        bloodBankRepository.save(bloodBank);

        ticket.setRedeem(true);
        Ticket redeemedTicket=ticketRepository.save(ticket);

        //now we will create an entry in the transaction table..
        Transaction transaction=createTransaction(ticket);
        return EntityToDto(redeemedTicket);
    }

    public List<TicketDto> DtoListfromEntityList(List<Ticket> tickets){
        List<TicketDto> ticketDtos=new ArrayList<>();
        tickets.stream().map((ticket -> {
            return ticketDtos.add(EntityToDto(ticket));
        })).collect(Collectors.toList());

        return ticketDtos;
    }

    public TicketDto EntityToDto(Ticket ticket){
        return modelMapper.map(ticket,TicketDto.class);
    }

    public Ticket getTicket(Integer ticketId){
        if(ticketRepository.findById(ticketId).isPresent()){
            return ticketRepository.findById(ticketId).get();
        }
        throw new NotFoundException("Ticket with the id "+ticketId+" is not present");
    }

    public String getUsernameFromToken(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public List<Ticket> fetchTicketsCorrespondingToBank(){
        //firstly we will fetch the manager username from the jwt token..
        String managerUsername=getUsernameFromToken();
        //now we will fetch the corresponding bank
        BloodBank bloodBank=bloodBankRepository.findByUsername(managerUsername);
        List<Ticket> tickets=ticketRepository.findByBankId(bloodBank.getBankId());
        return tickets;
    }

    public Transaction createTransaction(Ticket ticket){
        Transaction transaction=new Transaction();
        transaction.setBankId(ticket.getBankId());
        transaction.setBloodType(ticket.getBloodType());
        transaction.setQuantity(ticket.getQuantity());
        transaction.setCreatedOn(new Date());
        transaction.setTicketId(ticket.getTicketId());
        transaction.setUsername(ticket.getUsername());
        return transactionRepository.save(transaction);
    }
}
