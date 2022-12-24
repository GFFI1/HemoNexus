package com.example.BloodBankManagementSystem.services.impl;

import com.example.BloodBankManagementSystem.entities.*;
import com.example.BloodBankManagementSystem.exceptions.NotFoundException;
import com.example.BloodBankManagementSystem.payload.BloodBankDto;
import com.example.BloodBankManagementSystem.payload.BloodRequestFormDto;
import com.example.BloodBankManagementSystem.repositories.BloodBankRepository;
import com.example.BloodBankManagementSystem.repositories.BloodDetailRepository;
import com.example.BloodBankManagementSystem.repositories.RoleRepository;
import com.example.BloodBankManagementSystem.repositories.UserRepository;
import com.example.BloodBankManagementSystem.services.BloodBankService;
import com.example.BloodBankManagementSystem.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
public class BloodBankServiceImpl implements BloodBankService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    BloodBankRepository bloodBankRepository;

    @Autowired
    BloodDetailRepository bloodDetailRepository;

    @Autowired
    UserService userService;



    @Override
    public BloodBankDto createBloodBank(BloodBankDto bloodBankDto) {

        //firstly from the details we will create the manager of that blood-bank
        User managerUser=createManager(bloodBankDto);

        //now we will create a blood-bank..
        BloodBank bloodBank=DtoToBank(bloodBankDto);

        BloodBank savedBloodBank=bloodBankRepository.save(bloodBank);

        BloodBankDto bankDto=BankToDto(savedBloodBank);

        //we are doing it like this bcz their are some of the fields that are thier in the dto but not in entity
        bankDto.setFirstName(bloodBankDto.getFirstName());
        bankDto.setLastName(bloodBankDto.getLastName());
        bankDto.setManager_email(bloodBankDto.getManager_email());
        bankDto.setManager_mobileNumber(bloodBankDto.getManager_mobileNumber());
        bankDto.setPassword(bloodBankDto.getPassword());


        //after the creation of blood-bank we need to add Blood Detail...
        BloodBank savedBloodBank1=createBloodDetail(savedBloodBank);
        bankDto.setBloodDetailList(savedBloodBank1.getBloodDetailList());
        return bankDto;

    }

    @Override
    public BloodBankDto updateBloodBank(BloodBankDto bloodBankDto, Integer bankId) {
         BloodBank bloodBank=getBank(bankId);

         //the updation could be related to the bank or it could be related to the manager
         /*->can we make an assumption that if manager has to update it's detail it will do
         like a normal user and hit those api's defined in user-controller
         ->here we will handle the updation related to the bank only*/

        if(bloodBankDto.getBankName()!=null){
            bloodBank.setBankName(bloodBankDto.getBankName());
        }
        if(bloodBankDto.getCity()!=null){
            bloodBank.setCity(bloodBankDto.getCity());
        }
        if(bloodBankDto.getState()!=null){
            bloodBank.setState(bloodBankDto.getState());
        }
        if(bloodBankDto.getEmail()!=null){
            bloodBank.setEmail(bloodBankDto.getEmail());
        }
        if(bloodBankDto.getMobileNumber()!=null){
            bloodBank.setMobileNumber(bloodBankDto.getMobileNumber());
        }
        BloodBank updatedBloodBank=bloodBankRepository.save(bloodBank);
        return setRemainingField(updatedBloodBank);
    }

    @Override
    public List<BloodBankDto> getAllBloodBanks() {
        List<BloodBank> bloodBanks=bloodBankRepository.findAll();
        //here bloodBank will have only-selected details...like only the username of manager
        List<BloodBankDto> bloodBankDtos=new ArrayList<>();
        bloodBanks.stream().map((bloodBank)->{
            BloodBankDto bloodBankDto=setRemainingField(bloodBank);
            return bloodBankDtos.add(bloodBankDto);

        }).collect(Collectors.toList());
        return bloodBankDtos;
    }

    @Override
    public BloodBankDto getBloodBankById(Integer bankId) {
        //exception is handled their only...
        BloodBank bloodBank=getBank(bankId);
        return setRemainingField(bloodBank);
    }

    @Override
    public List<BloodBankDto> getBloodBankByCity(String city) {
        List<BloodBank> bloodBanks=bloodBankRepository.findByCity(city);
        List<BloodBankDto> bloodBankDtos=new ArrayList<>();
        bloodBanks.stream().map((bloodBank)->{
            BloodBankDto bloodBankDto=setRemainingField(bloodBank);
            return bloodBankDtos.add(bloodBankDto);

        }).collect(Collectors.toList());
        return bloodBankDtos;
    }

    @Override
    public List<BloodBankDto> getBloodBankByCityandType(BloodRequestFormDto bloodRequestDto) {
        //first of fall we will fetch all the blood-banks in that area(city)
        List<BloodBank> bloodBanks=bloodBankRepository.findByCity(bloodRequestDto.getCity());

        //now we will loop through the list and return all those that will have the desired type of blood
        List<BloodBank> requiredBanks=new ArrayList<>();

        bloodBanks.stream().map((bank)->{
            List<BloodDetail> bloodDetails=bank.getBloodDetailList();
            AtomicBoolean isPresent= new AtomicBoolean(false);

            bloodDetails.stream().map((bloodDetail)->{
                if(bloodDetail.getBloodType()==bloodRequestDto.getBloodType() && bloodDetail.getAmount()>0){
                    isPresent.set(true);
                }
                return  bloodDetail;
            }).collect(Collectors.toList());

            if(isPresent.get()){
                requiredBanks.add(bank);
            }
            return bank;
        }).collect(Collectors.toList());


        //now we need to convert these banks into the DTO's and then will send it back to the client-side

        List<BloodBankDto> bloodBankDtos=new ArrayList<>();
        requiredBanks.stream().map((bank)->{
            BloodBankDto bloodBankDto=setRemainingField(bank);
            return bloodBankDtos.add(bloodBankDto);
        }).collect(Collectors.toList());

        return bloodBankDtos;

    }

    @Override
    public void deleteBloodBank(Integer bankId) {
        BloodBank bloodBank=getBank(bankId);

        //before deleting the blood-bank we will also delete the manager associated witth it
        userService.deleteUser(bloodBank.getUsername());

        //now we will delete the bank
        bloodBankRepository.delete(bloodBank);
    }

    @Override
    public List<BloodBankDto> requestBloodFromOtherBanks(String bloodType) {
        //here alos the flow will be similar to that of the case when the normal user request for the blood
        String managerUsername=getUsernameFromToken();
        BloodBank bloodBank=bloodBankRepository.findByUsername(managerUsername);
        //now we will make the BloodRequestFormDto
        BloodRequestFormDto bloodRequestFormDto=generateBloodRequestDto(getType(bloodType),bloodBank);
        List<BloodBankDto> bloodBankDtos=getBloodBankByCityandType(bloodRequestFormDto);
        return bloodBankDtos;


    }

    public BloodType getType(String type){

        switch (type) {
            case "a+ve":
                return BloodType.APOS;
            case "a-ve":
                return BloodType.ANEG;
            case "b+ve":
                return BloodType.BPOS;
            case "b-ve":
                return BloodType.BNEG;
            case "ab+ve":
                return BloodType.ABPOS;
            case "ab-ve":
                return BloodType.ABNEG;
            case "o+ve":
                return BloodType.OPOS;
            case "o-ve":
                return BloodType.ONEG;
        }
        return null;
    }

    public BloodRequestFormDto generateBloodRequestDto(BloodType bloodType,BloodBank bloodBank){
        BloodRequestFormDto bloodRequestFormDto=new BloodRequestFormDto();
        bloodRequestFormDto.setBloodType(bloodType);
        bloodRequestFormDto.setCity(bloodBank.getCity());
        return bloodRequestFormDto;
    }

    public User createManager(BloodBankDto bloodBankDto){
        User managerUser=new User();
        managerUser.setUsername(bloodBankDto.getUsername());
        managerUser.setPassword(passwordEncoder.encode(bloodBankDto.getPassword()));
        managerUser.setEmail(bloodBankDto.getManager_email());
        managerUser.setLastName(bloodBankDto.getLastName());
        managerUser.setFirstName(bloodBankDto.getFirstName());
        managerUser.setMobileNumber(bloodBankDto.getManager_mobileNumber());

        //now we will set the role...
        Role managerRole=roleRepository.findById("ROLE_MANAGER").get();
        Set<Role> roles=new HashSet<>();
        roles.add(managerRole);
        managerUser.setRoles(roles);

        return userRepository.save(managerUser);
    }

    public BloodBank createBloodDetail(BloodBank savedBloodBank){
        BloodDetail bloodDetail1=new BloodDetail();
        bloodDetail1.setBlood_Bank_Id(savedBloodBank.getBankId());
        bloodDetail1.setBloodType(BloodType.APOS);
        bloodDetail1.setAmount(0L);


        BloodDetail bloodDetail2=new BloodDetail();
        bloodDetail2.setBlood_Bank_Id(savedBloodBank.getBankId());
        bloodDetail2.setBloodType(BloodType.ANEG);
        bloodDetail2.setAmount(0L);


        BloodDetail bloodDetail3=new BloodDetail();
        bloodDetail3.setBlood_Bank_Id(savedBloodBank.getBankId());
        bloodDetail3.setBloodType(BloodType.BPOS);
        bloodDetail3.setAmount(0L);


        BloodDetail bloodDetail4=new BloodDetail();
        bloodDetail4.setBlood_Bank_Id(savedBloodBank.getBankId());
        bloodDetail4.setBloodType(BloodType.BNEG);
        bloodDetail4.setAmount(0L);


        BloodDetail bloodDetail5=new BloodDetail();
        bloodDetail5.setBlood_Bank_Id(savedBloodBank.getBankId());
        bloodDetail5.setBloodType(BloodType.ABPOS);
        bloodDetail5.setAmount(0L);


        BloodDetail bloodDetail6=new BloodDetail();
        bloodDetail6.setBlood_Bank_Id(savedBloodBank.getBankId());
        bloodDetail6.setBloodType(BloodType.ABNEG);
        bloodDetail6.setAmount(0L);


        BloodDetail bloodDetail7=new BloodDetail();
        bloodDetail7.setBlood_Bank_Id(savedBloodBank.getBankId());
        bloodDetail7.setBloodType(BloodType.OPOS);
        bloodDetail7.setAmount(0L);


        BloodDetail bloodDetail8=new BloodDetail();
        bloodDetail8.setBlood_Bank_Id(savedBloodBank.getBankId());
        bloodDetail8.setBloodType(BloodType.ONEG);
        bloodDetail8.setAmount(0L);

        //add in the parent it will ne automatically saved in the child
        List<BloodDetail> bloodDetailList=savedBloodBank.getBloodDetailList();
        bloodDetailList.add(bloodDetail1);
        bloodDetailList.add(bloodDetail2);
        bloodDetailList.add(bloodDetail3);
        bloodDetailList.add(bloodDetail4);
        bloodDetailList.add(bloodDetail5);
        bloodDetailList.add(bloodDetail6);
        bloodDetailList.add(bloodDetail7);
        bloodDetailList.add(bloodDetail8);

        savedBloodBank.setBloodDetailList(bloodDetailList);
        return bloodBankRepository.save(savedBloodBank);
    }

    public BloodBank getBank(Integer bankId){
        if(bloodBankRepository.findById(bankId).isPresent()){
            return bloodBankRepository.findById(bankId).get();
        }
        throw new NotFoundException("Bank with the Id "+bankId+" is not present");
    }

    public BloodBankDto setRemainingField(BloodBank bloodBank){
        User manager=userRepository.findById(bloodBank.getUsername()).get();
        BloodBankDto bloodBankDto=BankToDto(bloodBank);
        bloodBankDto.setManager_email(manager.getEmail());
        bloodBankDto.setManager_mobileNumber(manager.getMobileNumber());
        bloodBankDto.setFirstName(manager.getFirstName());
        bloodBankDto.setLastName(manager.getLastName());
        bloodBankDto.setPassword(manager.getPassword());
        return bloodBankDto;

    }

    public String getUsernameFromToken(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public BloodBankDto BankToDto(BloodBank bloodBank){
        return modelMapper.map(bloodBank,BloodBankDto.class);
    }

    public BloodBank DtoToBank(BloodBankDto bloodBankDto){
        return modelMapper.map(bloodBankDto,BloodBank.class);
    }
}
