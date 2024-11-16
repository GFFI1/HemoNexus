package com.example.BloodBankManagementSystem.controllers;

import com.example.BloodBankManagementSystem.payload.BloodBankDto;
import com.example.BloodBankManagementSystem.payload.BloodRequestDto;
import com.example.BloodBankManagementSystem.payload.BloodRequestResponseDto;
import com.example.BloodBankManagementSystem.pojo.Response;
import com.example.BloodBankManagementSystem.pojo.Status;
import com.example.BloodBankManagementSystem.services.BloodRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/blood-request")
public class BloodRequestController {

    @Autowired
    private BloodRequestService bloodRequestService;

    @PostMapping
    public Response<BloodRequestResponseDto> createBloodRequest(@Valid @RequestBody BloodRequestDto bloodRequestDto){
        BloodRequestResponseDto bloodRequestResponseDto= bloodRequestService.createBloodRequest(bloodRequestDto);
        Response<BloodRequestResponseDto> response=new Response<>();
        response.setBody(bloodRequestResponseDto);
        response.setStatus(new Status(HttpStatus.OK.value(), true,"Blood Request is created"));
        return response;
    }

    @GetMapping(value = "/{requestId}")
    public Response<BloodRequestResponseDto> getBloodRequest(@PathVariable("requestId") Long requestId) {
        BloodRequestResponseDto bloodRequestResponseDto= bloodRequestService.getBloodRequestResponseDtoById(requestId);
        Response<BloodRequestResponseDto> response=new Response<>();
        response.setBody(bloodRequestResponseDto);
        response.setStatus(new Status(HttpStatus.OK.value(), true,"Return blood request for user"));
        return response;
    }

    @GetMapping(value = "/user")
    public Response<List<BloodRequestResponseDto>> getBloodRequestsForUser() {
        List<BloodRequestResponseDto> bloodRequestResponseDtos= bloodRequestService.getBloodRequestForUser();
        Response<List<BloodRequestResponseDto>> response=new Response<>();
        response.setBody(bloodRequestResponseDtos);
        response.setStatus(new Status(HttpStatus.OK.value(), true,"Return all blood requests for user"));
        return response;
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/admin")
    public Response<List<BloodRequestResponseDto>> getBloodRequestsForAdmin() {
        List<BloodRequestResponseDto> bloodRequestResponseDtos = bloodRequestService.getBloodRequestForAdmin();
        Response<List<BloodRequestResponseDto>> response=new Response<>();
        response.setBody(bloodRequestResponseDtos);
        response.setStatus(new Status(HttpStatus.OK.value(), true,"Return all blood requests for admin"));
        return response;
    }


    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @GetMapping(value = "/manager")
    public Response<List<BloodRequestResponseDto>> getBloodRequestsForManager() {
        List<BloodRequestResponseDto> bloodRequestResponseDtos = bloodRequestService.getBloodRequestForManager();
        Response<List<BloodRequestResponseDto>> response=new Response<>();
        response.setBody(bloodRequestResponseDtos);
        response.setStatus(new Status(HttpStatus.OK.value(), true,"Return all blood requests for manager"));
        return response;
    }


    @PutMapping(value = "/{requestId}")
    public Response<BloodRequestResponseDto> updateBloodRequest(@RequestBody BloodRequestDto bloodRequestDto, @PathVariable("requestId") Long requestId){
        BloodRequestResponseDto bloodRequestResponseDto= bloodRequestService.updateBloodRequest(bloodRequestDto, requestId);
        Response<BloodRequestResponseDto> response=new Response<>();
        response.setBody(bloodRequestResponseDto);
        response.setStatus(new Status(HttpStatus.OK.value(), true,"Blood Request is updated"));
        return response;
    }


    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @DeleteMapping(value = "/{requestId}")
    public Response<String> deleteBloodRequest(@PathVariable("requestId") Long requestId){
        String message = bloodRequestService.deleteBloodRequest(requestId);
        Response<String> response=new Response<>();
        response.setBody(message);
        response.setStatus(new Status(HttpStatus.OK.value(), true,"Blood Request is deleted"));
        return response;
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping(value = "/priority/{priority}")
    public Response<List<BloodRequestResponseDto>> getBloodRequestByPriority(@PathVariable("priority") String priority){
        List<BloodRequestResponseDto> bloodRequestResponseDtos=bloodRequestService.getBloodRequestByPriotity(priority);
        Response<List<BloodRequestResponseDto>> response=new Response<>();
        response.setBody(bloodRequestResponseDtos);
        response.setStatus(new Status(HttpStatus.OK.value(),true,"Fetched the request according to priority"));
        return response;
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping(value = "/approve/{requestId}")
    public Response<BloodRequestResponseDto> approveBloodRequest(@PathVariable("requestId") Integer requestId){
        BloodRequestResponseDto bloodRequestResponseDto=bloodRequestService.approveBloodRequest(requestId);
        Response<BloodRequestResponseDto> response=new Response<>();
        response.setBody(bloodRequestResponseDto);
        response.setStatus(new Status(HttpStatus.OK.value(),true,"Request is approved and the ticket is generated"));
        return response;
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping(value = "/transfer-bank-list/{requestId}")
    public Response<List<BloodBankDto>> listOfBankswhereRequestTransfer(@PathVariable("requestId") Integer requestId){
        List<BloodBankDto> bloodBankDtos=bloodRequestService.listOfBankswhereRequestTransfer(requestId);
        Response<List<BloodBankDto>> response=new Response<>();
        response.setBody(bloodBankDtos);
        response.setStatus(new Status(HttpStatus.OK.value(),true,"List of banks where the request can be transferred"));
        return response;
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping(value = "/transfer/{requestId}/{bankId}")
    public Response<BloodRequestResponseDto> transferBloodRequest(@PathVariable("requestId") Integer requestId,@PathVariable("bankId") Integer bankId){
        BloodRequestResponseDto bloodRequestResponseDto=bloodRequestService.transferRequest(requestId,bankId);
        Response<BloodRequestResponseDto> response=new Response<>();
        response.setBody(bloodRequestResponseDto);
        response.setStatus(new Status(HttpStatus.OK.value(),true,"Request is transferred to the appropriate bank"));
        return response;
    }





}
