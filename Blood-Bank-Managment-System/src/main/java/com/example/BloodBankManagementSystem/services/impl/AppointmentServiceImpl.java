package com.example.BloodBankManagementSystem.services.impl;

import com.example.BloodBankManagementSystem.entities.Appointment;
import com.example.BloodBankManagementSystem.entities.BloodBank;
import com.example.BloodBankManagementSystem.entities.Ticket;
import com.example.BloodBankManagementSystem.entities.User;
import com.example.BloodBankManagementSystem.exceptions.NotFoundException;
import com.example.BloodBankManagementSystem.payload.AppointmentRequestDto;
import com.example.BloodBankManagementSystem.payload.AppointmentResponseDto;
import com.example.BloodBankManagementSystem.repositories.AppointmentRepository;
import com.example.BloodBankManagementSystem.repositories.BloodBankRepository;
import com.example.BloodBankManagementSystem.repositories.TicketRepository;
import com.example.BloodBankManagementSystem.services.AppointmentService;
import com.example.BloodBankManagementSystem.services.CompaignService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    BloodBankRepository bloodBankRepository;

    @Autowired
    TicketRepository ticketRepository;


    @Override
    public AppointmentResponseDto createAppointment(AppointmentRequestDto appointmentRequestDto) {

        //firstly we will fetch the username from the token and then fetch the user
        String username= getUsernameFromToken();
        User user=userService.getUser(username);

        //now we will create the Appointment
        Appointment appointment=new Appointment();
        appointment.setApprove(false);
        appointment.setCreatedOn(new Date());
        appointment.setBloodType(user.getBloodType());
        appointment.setQuantity(appointmentRequestDto.getQuantity());
        appointment.setBankId(appointmentRequestDto.getBankId());
        appointment.setUsername(username);

        Appointment savedAppointment=appointmentRepository.save(appointment);
        return EntityToDto(savedAppointment);
    }

    //this will be for manager to list all the appointment that happened in his hospital
    @Override
    public List<AppointmentResponseDto> getAllAppointments() {
        List<Appointment> appointments=fetchTheListOfAppointmenForManager();
        return convertEntityListToDtoList(appointments);
    }

    @Override
    public List<AppointmentResponseDto> getAllPendingAppoinments() {
        List<Appointment> appointments=fetchTheListOfAppointmenForManager();
        List<Appointment> pending=new ArrayList<>();

        appointments.stream().peek((appointment -> {
            if(!appointment.getApprove()){
                pending.add(appointment);
            }
        })).collect(Collectors.toList());

        return convertEntityListToDtoList(pending);
    }

    @Override
    public AppointmentResponseDto getAppointmentById(Integer appointmentId) {
        Appointment appointment=getAppointment(appointmentId);
        return EntityToDto(appointment);
    }

    @Override
    public void deleteAppointment(Integer appointmentId) {
        Appointment appointment=getAppointment(appointmentId);
        appointmentRepository.delete(appointment);
    }

    @Override
    public AppointmentResponseDto approveAppointment(Integer appointmentId) {
        //here the ticket generation will happen when the manager will gonna to approve the request
        Appointment appointment=getAppointment(appointmentId);
        Ticket ticket=generateTicket(appointment);

        //now the request is approved so we need to update the appointment
        appointment.setApprove(true);
        Appointment approvedAppointment=appointmentRepository.save(appointment);

        return EntityToDto(approvedAppointment);
    }

    public AppointmentResponseDto EntityToDto(Appointment appointment){
        return modelMapper.map(appointment,AppointmentResponseDto.class);
    }

    public String getUsernameFromToken(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public List<AppointmentResponseDto> convertEntityListToDtoList(List<Appointment> appointments){
        List<AppointmentResponseDto> appointmentResponseDtos=new ArrayList<>();
        appointments.stream().map((appointment -> {
            return  appointmentResponseDtos.add(EntityToDto(appointment));
        })).collect(Collectors.toList());
        return appointmentResponseDtos;
    }

    public List<Appointment> fetchTheListOfAppointmenForManager(){
        //manager's username
        String username=getUsernameFromToken();
        //bank to which the manager belongs
        BloodBank bloodBank=bloodBankRepository.findByUsername(username);
        List<Appointment> appointments=appointmentRepository.findByBankId(bloodBank.getBankId());
        return appointments;
    }

    public Appointment getAppointment(Integer appointmentId){
        if(appointmentRepository.findById(appointmentId).isPresent()){
            return appointmentRepository.findById(appointmentId).get();
        }
        throw new NotFoundException("Appointment with the id "+appointmentId+" is not presnet");
    }

    public Ticket generateTicket(Appointment appointment){
        Ticket ticket=new Ticket();
        ticket.setBloodType(appointment.getBloodType());
        ticket.setQuantity(appointment.getQuantity());
        ticket.setUsername(appointment.getUsername());
        ticket.setDonor(true);  //appointment is for donor..
        ticket.setRedeem(false); //initially it will be false...
        ticket.setBankId(appointment.getBankId());
        ticket.setCreatedOn(new Date());
        //fetching the tomorrow's date
        Calendar calendar=Calendar.getInstance();
        Date today=calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR,1);
        Date tomorrow=calendar.getTime();
        ticket.setValidDate(tomorrow);
        return ticketRepository.save(ticket);
    }
}
