package com.example.BloodBankManagementSystem.services.impl;

import com.example.BloodBankManagementSystem.entities.BloodBank;
import com.example.BloodBankManagementSystem.entities.Compaign;
import com.example.BloodBankManagementSystem.exceptions.NotFoundException;
import com.example.BloodBankManagementSystem.payload.CompaignRequestDto;
import com.example.BloodBankManagementSystem.payload.CompaignResponseDto;
import com.example.BloodBankManagementSystem.repositories.BloodBankRepository;
import com.example.BloodBankManagementSystem.repositories.CompaignRepository;
import com.example.BloodBankManagementSystem.services.CompaignService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CompaignServiceImpl implements CompaignService {

    @Autowired
    BloodBankRepository bloodBankRepository;

    @Autowired
    CompaignRepository compaignRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public CompaignResponseDto createCompaign(CompaignRequestDto compaignRequestDto) throws ParseException {
        //we will first fetch the username from the token...
        String username = getUsernameFromToken();
        //from this username we will fetch the bank
        BloodBank bloodBank=bloodBankRepository.findByUsername(username);
        //now we will create the compaign and then send back the reponseDto to Controlller
        Compaign compaign=createCompaign(bloodBank,compaignRequestDto);
        return EntityToDto(compaign);
    }

    @Override
    public CompaignResponseDto updateCompaign(CompaignRequestDto compaignRequestDto, Integer compaignId) {
        Compaign compaign=getCompaign(compaignId);
        if(compaignRequestDto.getLocality()!=null){
            compaign.setLocality(compaignRequestDto.getLocality());
        }
        if(compaignRequestDto.getStart_Date()!=null){
            compaign.setStart_Date(compaignRequestDto.getStart_Date());
        }
        if(compaignRequestDto.getEnd_Date()!=null){
            compaign.setEnd_Date(compaignRequestDto.getEnd_Date());
        }
        Compaign updatedCompaign=compaignRepository.save(compaign);
        return EntityToDto(updatedCompaign);
    }

    @Override
    public List<CompaignResponseDto> getAllCompaigns() {
        //first fetch the username from the jwt-token
        String username = getUsernameFromToken();
        //now we will fetch the bank of this manager..
        BloodBank bank=bloodBankRepository.findByUsername(username);
        //now we will get-all the compaigns which have this id..
        List<Compaign> compaigns=compaignRepository.findByBankId(bank.getBankId());
        return getDtoListFromEntityList(compaigns);

    }

    @Override
    public List<CompaignResponseDto> getAllCompaignByLocation(String city) {
         List<Compaign> compaigns=compaignRepository.findByCity(city);
         return getDtoListFromEntityList(compaigns);
    }

    @Override
    public CompaignResponseDto getCompaignById(Integer compaignId) {
         Compaign compaign=getCompaign(compaignId);
         return EntityToDto(compaign);
    }

    @Override
    public void deleteCompaignById(Integer compaignId) {
          Compaign compaign=getCompaign(compaignId);
          compaignRepository.delete(compaign);
    }

    public Compaign createCompaign(BloodBank bloodBank,CompaignRequestDto compaignRequestDto) throws ParseException {
        Compaign compaign=new Compaign();
        compaign.setBankId(bloodBank.getBankId());
        compaign.setCity(bloodBank.getCity());
        compaign.setState(bloodBank.getState());

        Date date=new Date();
        SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy");
        String strDate=formatter.format(date);
        compaign.setCreatedOn(formatter.parse(strDate));

        compaign.setLocality(compaignRequestDto.getLocality());
        log.info("Start date is "+compaignRequestDto.getStart_Date());
        log.info("End date is "+compaignRequestDto.getEnd_Date());
        compaign.setStart_Date(compaignRequestDto.getStart_Date());
        compaign.setEnd_Date(compaignRequestDto.getEnd_Date());
        return compaignRepository.save(compaign);
    }

    public CompaignResponseDto EntityToDto(Compaign compaign){
        return modelMapper.map(compaign,CompaignResponseDto.class);
    }

    public Compaign getCompaign(Integer compaignId){
        if(compaignRepository.findById(compaignId).isPresent()){
            return compaignRepository.findById(compaignId).get();
        }
        throw new NotFoundException("Compaign with the id "+compaignId+" is not present in tha database");
    }

    public String getUsernameFromToken(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return username;
    }

    public List<CompaignResponseDto> getDtoListFromEntityList(List<Compaign> compaigns){
        List<CompaignResponseDto> compaignResponseDtos=new ArrayList<>();
        compaigns.stream().map((compaign)->{
            return compaignResponseDtos.add(EntityToDto(compaign));
        }).collect(Collectors.toList());
        return compaignResponseDtos;
    }
}
