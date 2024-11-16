package com.example.BloodBankManagementSystem.services;

import com.example.BloodBankManagementSystem.entities.BloodType;
import com.example.BloodBankManagementSystem.payload.BloodBankDto;
import com.example.BloodBankManagementSystem.payload.BloodRequestFormDto;

import java.util.List;

public interface BloodBankService {

    public BloodBankDto createBloodBank(BloodBankDto bloodBankDto);
    public BloodBankDto updateBloodBank(BloodBankDto bloodBankDto,Integer bankId);
    public List<BloodBankDto> getAllBloodBanks();
    public BloodBankDto getBloodBankById(Integer bankId);
    public List<BloodBankDto> getBloodBankByCity(String city);
    public List<BloodBankDto> getBloodBankByCityandType(BloodRequestFormDto bloodRequestDto);
    public void deleteBloodBank(Integer bankId);
    public List<BloodBankDto> requestBloodFromOtherBanks(String bloodType);
}
