package com.example.BloodBankManagementSystem.controllers;

import com.example.BloodBankManagementSystem.entities.User;
import com.example.BloodBankManagementSystem.payload.JwtAuthRequest;
import com.example.BloodBankManagementSystem.payload.JwtAuthResponse;
import com.example.BloodBankManagementSystem.payload.UserDto;
import com.example.BloodBankManagementSystem.pojo.Response;
import com.example.BloodBankManagementSystem.pojo.Status;
import com.example.BloodBankManagementSystem.repositories.UserRepository;
import com.example.BloodBankManagementSystem.security.JwtTokenHelper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping(value="/api/user")
public class AuthController {

    //for doing token related operations...
    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    //to load the user..
    @Autowired
    private UserDetailsService userDetailsService;

    //this is the bean and it can be auto-wired like this...
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;


    //we won't be getting userDto from the client-side..we are getting the JwtAuthRequest from the client-side
    @PostMapping(value = "/login")
    public ResponseEntity<Response<JwtAuthResponse>> loginUser(@RequestBody JwtAuthRequest request){
        authenticate(request.getUsername(),request.getPassword());
        UserDetails userDetails=userDetailsService.loadUserByUsername(request.getUsername());
        //now we will generate the token..
        String token=jwtTokenHelper.generateToken(userDetails);

        JwtAuthResponse response=new JwtAuthResponse();
        response.setJwtToken(token);

        User user=userRepository.findById(request.getUsername()).get();
        UserDto userDto=modelMapper.map(user, UserDto.class);

        response.setUserDto(userDto);

        Response res=new Response<JwtAuthResponse>();
        res.setBody(response);
        res.setStatus(new Status(HttpStatus.OK.value(),true,"Token is send from the server side"));

        return new ResponseEntity<>(res, HttpStatus.OK);

    }

    public void authenticate(String username,String password){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(username,password);
        //we may get an exception here that needs to be handled separately...
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }



}
