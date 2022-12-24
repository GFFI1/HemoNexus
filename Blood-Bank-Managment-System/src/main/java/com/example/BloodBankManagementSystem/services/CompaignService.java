package com.example.BloodBankManagementSystem.services;

import com.example.BloodBankManagementSystem.payload.CompaignRequestDto;
import com.example.BloodBankManagementSystem.payload.CompaignResponseDto;

import java.text.ParseException;
import java.util.List;

public interface CompaignService {

    public CompaignResponseDto createCompaign(CompaignRequestDto compaignRequestDto) throws ParseException;
    public CompaignResponseDto updateCompaign(CompaignRequestDto compaignRequestDto,Integer compaignId);
    public List<CompaignResponseDto> getAllCompaigns();
    public List<CompaignResponseDto> getAllCompaignByLocation(String city);
    public CompaignResponseDto getCompaignById(Integer compaignId);
    public void deleteCompaignById(Integer compaignId);

}
