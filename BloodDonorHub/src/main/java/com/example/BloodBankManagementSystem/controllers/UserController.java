package com.example.BloodBankManagementSystem.controllers;

import com.example.BloodBankManagementSystem.payload.UserDto;
import com.example.BloodBankManagementSystem.pojo.Response;
import com.example.BloodBankManagementSystem.pojo.Status;
import com.example.BloodBankManagementSystem.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.List;

//just for the check
@RestController
@RequestMapping(value = "/api/user")
@Slf4j
public class UserController {

    @Autowired
    UserService userService;

    @PostConstruct //their will be only one method that could be annotated with PostConstruct in a class
    public void initialConfiguration(){
        log.info("Adding the roles and doing the admin registration");
        userService.initialConfiguration();
    }

    @PostMapping(value = "/register")
    public Response<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        UserDto userDto1=userService.createUser(userDto);
        Response<UserDto> response=new Response<>();
        response.setBody(userDto1);
        response.setStatus(new Status(HttpStatus.OK.value(), true,"User is created successfully"));
        return response;
    }

    //here validation is not required bcz it is not necessary that all the fields will be updated
    @PutMapping(value = "/{username}")
    public Response<UserDto> updateUser(@RequestBody UserDto userDto,@PathVariable("username") String username){
        UserDto updatedUserDto=userService.updateUser(userDto,username);
        Response<UserDto> response=new Response<>();
        response.setBody(updatedUserDto);
        response.setStatus(new Status(HttpStatus.OK.value(), true,"User is updated successfully"));
        return response;
    }


    @GetMapping
    public Response<List<UserDto>> getAllUsers(){
        List<UserDto> userDtoList=userService.getAllUsers();
        Response<List<UserDto>> response=new Response<>();
        response.setBody(userDtoList);
        response.setStatus(new Status(HttpStatus.OK.value(), true,"Fetched all the users"));
        return response;
    }

    @GetMapping(value="/{username}")
    public Response<UserDto> getUserById(@PathVariable("username") String username){
        UserDto userDto=userService.getUserById(username);
        Response<UserDto> response=new Response<>();
        response.setBody(userDto);
        response.setStatus(new Status(HttpStatus.OK.value(), true,"Fetched user by Id"));
        return response;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value="/{username}")
    public Response<String> deleteUser(@PathVariable("username") String username){
        userService.deleteUser(username);
        Response<String> response=new Response<>();
        response.setBody("User is deleted successfully");
        response.setStatus(new Status(HttpStatus.OK.value(), true,"Deletion Done"));
        return response;
    }

    @GetMapping(value = "/logged")
    public Response<UserDto> getLoggedUser(){
        UserDto userDto=userService.getLoggedUser();
        Response<UserDto> response=new Response<>();
        response.setBody(userDto);
        response.setStatus(new Status(HttpStatus.OK.value(), true,"Fetched the currently logged in user"));
        return response;
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping(value = "/donors")
    public Response<List<UserDto>> getAllDonors(){
        List<UserDto> userDtoList=userService.getAllDonors();
        Response<List<UserDto>> response=new Response<>();
        response.setBody(userDtoList);
        response.setStatus(new Status(HttpStatus.OK.value(), true,"Fetched all the users"));
        return response;
    }
}
