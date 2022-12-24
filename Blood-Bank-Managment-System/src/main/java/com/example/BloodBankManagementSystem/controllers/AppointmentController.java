package com.example.BloodBankManagementSystem.controllers;

import com.example.BloodBankManagementSystem.payload.AppointmentRequestDto;
import com.example.BloodBankManagementSystem.payload.AppointmentResponseDto;
import com.example.BloodBankManagementSystem.pojo.Response;
import com.example.BloodBankManagementSystem.pojo.Status;
import com.example.BloodBankManagementSystem.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/appointment")
public class AppointmentController {

    @Autowired
    AppointmentService appointmentService;


    @PostMapping
    public Response<AppointmentResponseDto> createAppointment(@RequestBody AppointmentRequestDto appointmentRequestDto){
        AppointmentResponseDto appointmentResponseDto=appointmentService.createAppointment(appointmentRequestDto);
        Response<AppointmentResponseDto> response=new Response<>();
        response.setBody(appointmentResponseDto);
        response.setStatus(new Status(HttpStatus.OK.value(), true,"Appointment is created"));
        return response;
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping
    public Response<List<AppointmentResponseDto>> getAllAppointments(){
        List<AppointmentResponseDto> appointmentResponseDtos=appointmentService.getAllAppointments();
        Response<List<AppointmentResponseDto>> response=new Response<>();
        response.setBody(appointmentResponseDtos);
        response.setStatus(new Status(HttpStatus.OK.value(), true,"Fetched all the appointments"));
        return response;
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping(value = "/pending")
    public Response<List<AppointmentResponseDto>> getAllPendingAppointments(){
        List<AppointmentResponseDto> appointmentResponseDtos=appointmentService.getAllPendingAppoinments();
        Response<List<AppointmentResponseDto>> response=new Response<>();
        response.setBody(appointmentResponseDtos);
        response.setStatus(new Status(HttpStatus.OK.value(), true,"Fetched all the Pending appointments"));
        return response;
    }

    @PreAuthorize("hasRole('MANAGER')")
    @DeleteMapping(value = "/{appointmentId}")
    public Response<String> deleteAppointmentById(@PathVariable("appointmentId") Integer appointmentId){
        appointmentService.deleteAppointment(appointmentId);
        Response<String> response=new Response<>();
        response.setBody("Appointment is deleted");
        response.setStatus(new Status(HttpStatus.OK.value(), true,"Deletion is done successfull"));
        return response;
    }

    @GetMapping(value = "/{appointmentId}")
    public Response<AppointmentResponseDto> getAppointmentById(@PathVariable("appointmentId") Integer appointmentId){
        AppointmentResponseDto appointmentResponseDto=appointmentService.getAppointmentById(appointmentId);
        Response<AppointmentResponseDto> response=new Response<>();
        response.setBody(appointmentResponseDto);
        response.setStatus(new Status(HttpStatus.OK.value(), true,"Fetched the appointment By Id"));
        return response;
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping(value = "/approve/{appointmentId}")
    public Response<AppointmentResponseDto> approveAppointment(@PathVariable("appointmentId") Integer appointmentId){
        AppointmentResponseDto appointmentResponseDto=appointmentService.approveAppointment(appointmentId);
        Response<AppointmentResponseDto> response=new Response<>();
        response.setBody(appointmentResponseDto);
        response.setStatus(new Status(HttpStatus.OK.value(), true,"Appointment is approved and ticket is generated"));
        return response;
    }


}
