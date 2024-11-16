package com.example.BloodBankManagementSystem.services.impl;

import com.example.BloodBankManagementSystem.entities.*;
import com.example.BloodBankManagementSystem.exceptions.NotFoundException;
import com.example.BloodBankManagementSystem.payload.BloodBankDto;
import com.example.BloodBankManagementSystem.payload.BloodRequestDto;
import com.example.BloodBankManagementSystem.payload.BloodRequestFormDto;
import com.example.BloodBankManagementSystem.payload.BloodRequestResponseDto;
import com.example.BloodBankManagementSystem.repositories.BloodBankRepository;
import com.example.BloodBankManagementSystem.repositories.BloodRequestRepository;
import com.example.BloodBankManagementSystem.repositories.TicketRepository;
import com.example.BloodBankManagementSystem.services.BloodBankService;
import com.example.BloodBankManagementSystem.services.BloodRequestService;
import org.apache.coyote.Request;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BloodRequestServiceImpl implements BloodRequestService {

    @Autowired
    private BloodRequestRepository bloodRequestRepository;


    @Autowired
    ModelMapper modelMapper;

    @Autowired
    BloodBankRepository bloodBankRepository;

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    BloodBankService bankService;


    public BloodRequest getBloodRequestById(Long requestID) {
        if(bloodRequestRepository.findById(requestID).isPresent()) {
            BloodRequest bloodRequest = bloodRequestRepository.findById(requestID).get();
            return bloodRequest;
        }
        throw new NotFoundException("Blood Request with Id " + requestID + " not found");
    }


    public BloodRequestResponseDto getBloodRequestResponseDtoById(Long requestID) {
        if(bloodRequestRepository.findById(requestID).isPresent()) {
            BloodRequest bloodRequest = bloodRequestRepository.findById(requestID).get();
            BloodRequestResponseDto bloodRequestResponseDto = bloodRequestToResponseDto(bloodRequest);
            return bloodRequestResponseDto;
        }
        throw new NotFoundException("Blood Request with Id " + requestID + " not found");
    }

    public List<BloodRequestResponseDto> getBloodRequestForUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<BloodRequest> bloodRequests = bloodRequestRepository.findByUsername(username);
        List<BloodRequestResponseDto> bloodRequestResponseDtos = new ArrayList<>();
        for(BloodRequest bloodRequest: bloodRequests) {
            bloodRequestResponseDtos.add(bloodRequestToResponseDto(bloodRequest));
        }
        return bloodRequestResponseDtos;
    }


    public List<BloodRequestResponseDto> getBloodRequestForAdmin() {
        List<BloodRequest> bloodRequests = bloodRequestRepository.findAll();
        List<BloodRequestResponseDto> bloodRequestResponseDtos = new ArrayList<>();
        for(BloodRequest bloodRequest: bloodRequests) {
            bloodRequestResponseDtos.add(bloodRequestToResponseDto(bloodRequest));
        }
        return bloodRequestResponseDtos;
    }

    public List<BloodRequestResponseDto> getBloodRequestForManager() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        BloodBank bloodBank = bloodBankRepository.findByUsername(username);
        List<BloodRequest> bloodRequests = bloodRequestRepository.findByBankId(bloodBank.getBankId());
        return DtoListFromEntityList(bloodRequests);
    }


    public BloodRequestResponseDto createBloodRequest(BloodRequestDto bloodRequestDto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        BloodRequest bloodRequest = bloodRequestDtoToBloodRequest(bloodRequestDto);
        bloodRequest.setCreatedOn(new Date());
        bloodRequest.setUsername(username);
        bloodRequest.setStatusType(StatusType.PENDING); //initially it will be set to pending only...
        BloodRequest savedRequest = bloodRequestRepository.save(bloodRequest);
        BloodRequestResponseDto requestResponseDto = bloodRequestToResponseDto(savedRequest);
        return requestResponseDto;
    }


    public BloodRequestResponseDto updateBloodRequest(BloodRequestDto bloodRequestDto, Long requestId) {

        BloodRequest bloodRequest = getBloodRequestById(requestId);

        if(bloodRequestDto.getBloodType() != null) {
            bloodRequest.setBloodType(bloodRequestDto.getBloodType());
        }

        if(bloodRequestDto.getBankId() != null) {
            bloodRequest.setBankId(bloodRequestDto.getBankId());
        }

        if(bloodRequestDto.getQuantity() != null) {
            bloodRequest.setQuantity(bloodRequestDto.getQuantity());
        }

        BloodRequest savedRequest = bloodRequestRepository.save(bloodRequest);
        BloodRequestResponseDto requestResponseDto = bloodRequestToResponseDto(savedRequest);
        return requestResponseDto;
    }


    public String deleteBloodRequest(Long requestId) {
        BloodRequest bloodRequest = getBloodRequestById(requestId);
        bloodRequestRepository.delete(bloodRequest);
        return "Blood Request with Id " + requestId + " is deleted successfully";
    }

    @Override
    public List<BloodRequestResponseDto> getBloodRequestByPriotity(String priority) {
        List<BloodRequest> bloodRequests=new ArrayList<>();
        if(priority.equalsIgnoreCase("High")){
            bloodRequests=bloodRequestRepository.findByPriorityType(PriorityType.HIGH);
        }
        else if(priority.equalsIgnoreCase("Medium")){
            bloodRequests=bloodRequestRepository.findByPriorityType(PriorityType.MEDIUM);
        }
        else if(priority.equalsIgnoreCase("Low")){
            bloodRequests=bloodRequestRepository.findByPriorityType(PriorityType.LOW);
        }
        return DtoListFromEntityList(bloodRequests);
    }

    @Override
    public BloodRequestResponseDto approveBloodRequest(Integer bloodRequestId) {
        //now the manager will gonna to approve the request and the ticket generation will take place
        BloodRequest bloodRequest=getBloodRequestById((long)bloodRequestId);

        Ticket ticket=generateTicket(bloodRequest);
        //now the request is approved so we should change the status of it..
        bloodRequest.setStatusType(StatusType.APPROVE);

        BloodRequest approvedBloodRequest=bloodRequestRepository.save(bloodRequest);
        return bloodRequestToResponseDto(approvedBloodRequest);
    }

    //will return the list of potential-banks where the blood can be transferred..
    //potential-bank -> location(city), type of blood, quantity of blood
    @Override
    public List<BloodBankDto> listOfBankswhereRequestTransfer(Integer requestId) {
        /*->the transfer of request takes in two steps in the first step by clicking on the transfer
        we will show a list of banks who are located at that location and have the desired type of blood
        in the desired quantity.
        ->then in the second step manager will choose the bank among the list and transfer will happen*/
        BloodRequest request=getBloodRequestById((long)requestId);
        BloodRequestFormDto bloodRequestFormDto=generateBloodRequestFormDto(request);

        //now we will return all the banks that satisfy this bloodRequestDto
        List<BloodBankDto> bloodBankDtos=bankService.getBloodBankByCityandType(bloodRequestFormDto);
        /*now uptill now we have filteted the banks that have the type of blood and location...
        and now to transfer it the transfered bank should alos have the quantity.*/

        return getBanksThatHaveQuantity(bloodBankDtos,request.getBloodType(),request.getQuantity(),request.getBankId());

    }

    @Override
    public BloodRequestResponseDto transferRequest(Integer requestId,Integer bankId) {
        BloodRequest request=getBloodRequestById((long)requestId);
        request.setBankId(bankId);
        request.setStatusType(StatusType.TRANSFER);
        BloodRequest transferredRequest=bloodRequestRepository.save(request);
        return bloodRequestToResponseDto(transferredRequest);
    }

    private List<BloodBankDto> getBanksThatHaveQuantity(List<BloodBankDto> bloodBankDtos,BloodType requiredType,Integer requiredAmount,Integer currentBankId) {
        List<BloodBankDto> bankDtos=new ArrayList<>();

        bloodBankDtos.stream().map((bloodBankDto -> {
            //here we wil check for the blood-details
            List<BloodDetail> bloodDetails=bloodBankDto.getBloodDetailList();

            //now we will loop through the blood-details and check for type and quantity
            bloodDetails.stream().map((bloodDetail -> {
                if(bloodDetail.getBloodType().compareTo(requiredType)==0 && bloodDetail.getAmount()>=requiredAmount && bloodBankDto.getBankId()!=currentBankId){
                    bankDtos.add(bloodBankDto);
                }
                return bloodDetail;
            })).collect(Collectors.toList());
            return bloodBankDto;
        })).collect(Collectors.toList());

        return bankDtos;
    }

    public BloodRequestFormDto generateBloodRequestFormDto(BloodRequest request){
        BloodRequestFormDto bloodRequestFormDto=new BloodRequestFormDto();
        bloodRequestFormDto.setBloodType(request.getBloodType());
        BloodBank bloodBank=bloodBankRepository.findById(request.getBankId()).get();
        bloodRequestFormDto.setCity(bloodBank.getCity());
        return bloodRequestFormDto;
    }

    private Ticket generateTicket(BloodRequest bloodRequest) {
        Ticket ticket=new Ticket();
        ticket.setRedeem(false); //will be redeemed when the user comes manually at the hospital to take the blood

        //fetching the tomorrow's date
        Calendar calendar=Calendar.getInstance();
        Date today=calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR,1);
        Date tomorrow=calendar.getTime();
        ticket.setValidDate(tomorrow);

        ticket.setCreatedOn(today);
        ticket.setValidDate(tomorrow);
        ticket.setDonor(false); //bcz it is taking the blood..
        ticket.setUsername(bloodRequest.getUsername());
        ticket.setQuantity(bloodRequest.getQuantity());
        ticket.setBloodType(bloodRequest.getBloodType());
        ticket.setBankId(bloodRequest.getBankId());
        return ticketRepository.save(ticket);

    }

    public BloodRequestResponseDto bloodRequestToResponseDto(BloodRequest bloodRequest){
        return modelMapper.map(bloodRequest, BloodRequestResponseDto.class);
    }

    public BloodRequest bloodRequestDtoToBloodRequest(BloodRequestDto bloodRequestDto){
        return modelMapper.map(bloodRequestDto, BloodRequest.class);
    }

    public List<BloodRequestResponseDto> DtoListFromEntityList(List<BloodRequest> bloodRequests){
        List<BloodRequestResponseDto> bloodRequestResponseDtos=new ArrayList<>();
        bloodRequests.stream().map((bloodRequest -> {
            return bloodRequestResponseDtos.add(bloodRequestToResponseDto(bloodRequest));
        })).collect(Collectors.toList());
        return  bloodRequestResponseDtos;
    }
}
