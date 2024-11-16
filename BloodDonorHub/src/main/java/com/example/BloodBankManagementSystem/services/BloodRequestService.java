package com.example.BloodBankManagementSystem.services;

import com.example.BloodBankManagementSystem.entities.BloodRequest;
import com.example.BloodBankManagementSystem.payload.BloodBankDto;
import com.example.BloodBankManagementSystem.payload.BloodRequestDto;
import com.example.BloodBankManagementSystem.payload.BloodRequestResponseDto;

import java.util.List;

public interface BloodRequestService {

    public BloodRequest getBloodRequestById(Long requestID);
    public BloodRequestResponseDto getBloodRequestResponseDtoById(Long requestID);
    public List<BloodRequestResponseDto> getBloodRequestForUser();
    public List<BloodRequestResponseDto> getBloodRequestForAdmin();
    public List<BloodRequestResponseDto> getBloodRequestForManager();
    public BloodRequestResponseDto createBloodRequest(BloodRequestDto bloodRequestDto);
    public BloodRequestResponseDto updateBloodRequest(BloodRequestDto bloodRequestDto, Long requestId);
    public String deleteBloodRequest(Long requestId);
    public List<BloodRequestResponseDto> getBloodRequestByPriotity(String priority);
    public BloodRequestResponseDto approveBloodRequest(Integer bloodRequestId);
    public List<BloodBankDto> listOfBankswhereRequestTransfer(Integer requestId);
    public BloodRequestResponseDto transferRequest(Integer requestId,Integer bankId);
}
