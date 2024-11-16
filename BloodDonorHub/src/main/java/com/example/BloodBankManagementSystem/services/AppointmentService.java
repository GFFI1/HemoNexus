package com.example.BloodBankManagementSystem.services;

import com.example.BloodBankManagementSystem.payload.AppointmentRequestDto;
import com.example.BloodBankManagementSystem.payload.AppointmentResponseDto;

import java.util.List;

public interface AppointmentService {

    public AppointmentResponseDto createAppointment(AppointmentRequestDto appointmentRequestDto);
    public List<AppointmentResponseDto> getAllAppointments();
    public List<AppointmentResponseDto> getAllPendingAppoinments();
    public AppointmentResponseDto getAppointmentById(Integer appointmentId);
    public void deleteAppointment(Integer appointmentId);

    //this will lead to the ticket generation
    public AppointmentResponseDto approveAppointment(Integer appointmentId);
}
