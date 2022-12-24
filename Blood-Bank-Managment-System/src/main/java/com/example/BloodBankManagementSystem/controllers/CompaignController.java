package com.example.BloodBankManagementSystem.controllers;

import com.example.BloodBankManagementSystem.payload.CompaignRequestDto;
import com.example.BloodBankManagementSystem.payload.CompaignResponseDto;
import com.example.BloodBankManagementSystem.pojo.Response;
import com.example.BloodBankManagementSystem.pojo.Status;
import com.example.BloodBankManagementSystem.services.CompaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/compaign")
public class CompaignController {

    @Autowired
    CompaignService compaignService;

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping
    public Response<CompaignResponseDto> createCompaign(@RequestBody CompaignRequestDto compaignRequestDto) throws ParseException {
        CompaignResponseDto compaignResponseDto=compaignService.createCompaign(compaignRequestDto);

        Response<CompaignResponseDto> response=new Response<>();
        response.setBody(compaignResponseDto);
        response.setStatus(new Status(HttpStatus.OK.value(), true,"Compaign is created"));
        return response;

    }


    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping(value="/{compaignId}")
    public Response<CompaignResponseDto> updateCompaign(@RequestBody CompaignRequestDto compaignRequestDto,@PathVariable("compaignId") Integer compaignId){
        CompaignResponseDto compaignResponseDto=compaignService.updateCompaign(compaignRequestDto,compaignId);

        Response<CompaignResponseDto> response=new Response<>();
        response.setBody(compaignResponseDto);
        response.setStatus(new Status(HttpStatus.OK.value(), true,"Compaign is created"));
        return response;
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping
    public Response<List<CompaignResponseDto>> getAllCompaigns(){
        List<CompaignResponseDto> compaignResponseDtos=compaignService.getAllCompaigns();
        Response<List<CompaignResponseDto>> response=new Response<>();
        response.setBody(compaignResponseDtos);
        response.setStatus(new Status(HttpStatus.OK.value(), true,"Fetched all the compaigns of particular bank"));
        return response;

    }

    @GetMapping(value = "/donate/{city}")
    public Response<List<CompaignResponseDto>> getAllCompaignByLocation(@PathVariable("city") String city){
        List<CompaignResponseDto> compaignResponseDtos=compaignService.getAllCompaignByLocation(city);
        Response<List<CompaignResponseDto>> response=new Response<>();
        response.setBody(compaignResponseDtos);
        response.setStatus(new Status(HttpStatus.OK.value(), true,"Fetched all the compaigns according to the location"));
        return response;
    }

    @GetMapping(value="/{compaignId}")
    public Response<CompaignResponseDto> getCompaignById(@PathVariable("compaignId") Integer compaignId){
        CompaignResponseDto compaignResponseDto=compaignService.getCompaignById(compaignId);
        Response<CompaignResponseDto> response=new Response<>();
        response.setBody(compaignResponseDto);
        response.setStatus(new Status(HttpStatus.OK.value(), true,"Fetched the compaign from the Id"));
        return response;
    }

    @PreAuthorize("hasRole('MANAGER')")
    @DeleteMapping(value="/{compaignId}")
    public Response<String> deleteCompaignById(@PathVariable("compaignId") Integer compaignId){
        compaignService.deleteCompaignById(compaignId);
        Response<String> response=new Response<>();
        response.setBody("Compaign is deleted successfully");
        response.setStatus(new Status(HttpStatus.OK.value(), true,"Deletion Done"));
        return response;
    }




}
