package com.example.BloodBankManagementSystem.controllers;

import com.example.BloodBankManagementSystem.entities.BloodType;
import com.example.BloodBankManagementSystem.payload.BloodBankDto;
import com.example.BloodBankManagementSystem.payload.BloodRequestFormDto;
import com.example.BloodBankManagementSystem.pojo.Response;
import com.example.BloodBankManagementSystem.pojo.Status;
import com.example.BloodBankManagementSystem.services.BloodBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/blood-bank")
public class BloodBankController {

    @Autowired
    BloodBankService bloodBankService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Response<BloodBankDto> createBloodBank(@Valid @RequestBody BloodBankDto bloodBankDto){
        BloodBankDto bankDto=bloodBankService.createBloodBank(bloodBankDto);
        Response<BloodBankDto> response=new Response<>();
        response.setBody(bankDto);
        response.setStatus(new Status(HttpStatus.OK.value(), true,"Bank and Manager is created"));
        return response;
    }

    @GetMapping(value="/{bankId}")
    public Response<BloodBankDto> getBloodBankById(@PathVariable("bankId") Integer bankId){
        BloodBankDto bloodBankDto=bloodBankService.getBloodBankById(bankId);
        Response<BloodBankDto> response=new Response<>();
        response.setBody(bloodBankDto);
        response.setStatus(new Status(HttpStatus.OK.value(), true,"Fetched the bank by the id"));
        return response;
    }

    @GetMapping
    public Response<List<BloodBankDto>> getAllBloodBanks(){
        List<BloodBankDto> bankDtoList=bloodBankService.getAllBloodBanks();
        Response<List<BloodBankDto>> response=new Response<>();
        response.setBody(bankDtoList);
        response.setStatus(new Status(HttpStatus.OK.value(), true,"Fetched all the Banks"));
        return response;
    }

    @GetMapping(value = "/donate/{city}")
    public Response<List<BloodBankDto>> getBloodBankByCity(@PathVariable("city") String city){
        List<BloodBankDto> bloodBankDtos=bloodBankService.getBloodBankByCity(city);
        Response<List<BloodBankDto>> response=new Response<>();
        response.setBody(bloodBankDtos);
        response.setStatus(new Status(HttpStatus.OK.value(), true,"Banks fetched according to city"));
        return response;
    }


    @PostMapping(value = "/request")
    public Response<List<BloodBankDto>> getBloodBankByCityandType(@RequestBody BloodRequestFormDto bloodRequestDto){
        List<BloodBankDto> bloodBankDtos=bloodBankService.getBloodBankByCityandType(bloodRequestDto);
        Response<List<BloodBankDto>> response=new Response<>();
        response.setBody(bloodBankDtos);
        response.setStatus(new Status(HttpStatus.OK.value(), true,"Fetched the rquired banks from where the blood can be requested"));
        return  response;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/{bankId}")
    public Response<BloodBankDto> updateBloodBank(@RequestBody BloodBankDto bloodBankDto,@PathVariable("bankId") Integer bankId){
        BloodBankDto bankDto=bloodBankService.updateBloodBank(bloodBankDto,bankId);
        Response<BloodBankDto> response=new Response<>();
        response.setBody(bankDto);
        response.setStatus(new Status(HttpStatus.OK.value(), true,"Blood Bank is updated"));
        return response;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/{bankId}")
    public Response<String> deleteBankById(@PathVariable("bankId") Integer bankId){
        bloodBankService.deleteBloodBank(bankId);
        Response<String> response=new Response<>();
        response.setBody("Blood bank and it's associated manager is deleted");
        response.setStatus(new Status(HttpStatus.OK.value(), true,"Deletion Done"));
        return response;
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping(value = "/request/{bloodType}")
    public Response<List<BloodBankDto>> requestBloodFromAnotherBank(@PathVariable("bloodType") String bloodType){
        List<BloodBankDto> bloodBankDtos=bloodBankService.requestBloodFromOtherBanks(bloodType);
        Response<List<BloodBankDto>> response=new Response<>();
        response.setBody(bloodBankDtos);
        response.setStatus(new Status(HttpStatus.OK.value(), true,"Fetched the rquired banks from where the blood can be requested"));
        return  response;
    }





}
