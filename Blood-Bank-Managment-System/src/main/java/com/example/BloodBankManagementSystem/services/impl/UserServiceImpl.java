package com.example.BloodBankManagementSystem.services.impl;


import com.example.BloodBankManagementSystem.entities.*;
import com.example.BloodBankManagementSystem.exceptions.NotFoundException;
import com.example.BloodBankManagementSystem.payload.UserDto;
import com.example.BloodBankManagementSystem.repositories.BloodBankRepository;
import com.example.BloodBankManagementSystem.repositories.RoleRepository;
import com.example.BloodBankManagementSystem.repositories.TicketRepository;
import com.example.BloodBankManagementSystem.repositories.UserRepository;
import com.example.BloodBankManagementSystem.security.JwtTokenHelper;
import com.example.BloodBankManagementSystem.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    BloodBankRepository bloodBankRepository;

    @Autowired
    TicketRepository ticketRepository;



    @Override
    public void initialConfiguration() {
        //here we will first make an entry inside the role-table

        //this if will make sure that the entry will be done only once...
        log.info("method inside the service is triggered");
        if(roleRepository.count()!=3){
            addRole();
        }
        //now we will add the admin user..
        if(!userRepository.findById("admin").isPresent()){
            addAdmin();
        }
    }

    public void addRole(){
        Role userRole=new Role();
        userRole.setRoleName("ROLE_USER");
        userRole.setRoleDiscription("It is a normal user can request and donate the blood");

        Role managerRole=new Role();
        managerRole.setRoleName("ROLE_MANAGER");
        managerRole.setRoleDiscription("Manager is linked to every bank and will approve the request and appointment and generate the tickets");

        Role adminRole=new Role();
        adminRole.setRoleName("ROLE_ADMIN");
        adminRole.setRoleDiscription("Admin will add the blood bank and the corresponding manger associated with it");


        roleRepository.save(userRole);
        roleRepository.save(managerRole);
        roleRepository.save(adminRole);
        log.info("Role is added successfully");
    }

    public void addAdmin(){
        User user=new User();
        user.setUsername("admin");
        user.setPassword(getEncodedPassword("pass123"));
        user.setFirstName("Raj");
        user.setLastName("Patel");
        user.setEmail("admin@gmail.com");
        user.setMobileNumber("1234567890");
        //setting the role to the admin
        Set<Role> roleSet=new HashSet<>();
        Role adminRole=roleRepository.findById("ROLE_ADMIN").get();
        roleSet.add(adminRole);
        user.setRoles(roleSet);
        user.setBloodType(BloodType.ABNEG);
        //now will save the admin user in the database
        userRepository.save(user);
        log.info("Admin is added successfully");
    }
    //wherever we need to handle the exception this method will be called...
    public User getUser(String username){
        if(userRepository.findById(username).isPresent()){
            return userRepository.findById(username).get();
        }
        throw new NotFoundException("User with the username "+username+" not found in the database");
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user=DToToUser(userDto);
        //we will have to encode the password before saving it to the database...
        user.setPassword(getEncodedPassword(userDto.getPassword()));
        //we also need to assign the role of this user..it will be user.
        Role userRole=roleRepository.findById("ROLE_USER").get();
        Set<Role> roleSet=new HashSet<>();
        roleSet.add(userRole);
        user.setRoles(roleSet);
        //here we have added the roles in the user so it should need to be mapped automatically by the mapper...
        //be-aware that for the mapping the field name should be same in the entity and the DTO
        User savedUser=userRepository.save(user);
        return UserToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto,String username) {
        User user=getUser(username);
        user.setMobileNumber(userDto.getMobileNumber());
        user.setEmail(userDto.getEmail());
        //it means he wants to update the password
        if(userDto.getPassword()!=null){
            user.setPassword(getEncodedPassword(userDto.getPassword())); //here alos we will do the encoding...
        }
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());

        User updatedUser=userRepository.save(user);
        return UserToDto(updatedUser);
    }

    @Override
    public void deleteUser(String username) {
        User user=getUser(username);
        //first delete the entry inside the user_role table then it will work fine....else giving fk exception
        userRepository.delete(user);
    }

    @Override
    public UserDto getUserById(String username) {
        User user=getUser(username);
        return UserToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> userList=userRepository.findAll();
        List<UserDto> userDtoList=new ArrayList<>();
        userList.stream().map((user)-> userDtoList.add(UserToDto(user))
        ).collect(Collectors.toList());
        return userDtoList;
    }

    //will fetch the username from the token
    @Override
    public UserDto getLoggedUser() {
        String username = getUsernameFromToken();
        User user=userRepository.findById(username).get();
        return UserToDto(user);
    }

    @Override
    public List<UserDto> getAllDonors() {
        String managerUsername=getUsernameFromToken();
        BloodBank bloodBank=bloodBankRepository.findByUsername(managerUsername);
        List<Ticket> tickets=ticketRepository.findByBankId(bloodBank.getBankId());

        return listOfDonors(tickets);
    }

    private List<UserDto> listOfDonors(List<Ticket> tickets) {
        List<Ticket> donorTickets=getDonorTickets(tickets);
        List<UserDto> userDtoList=new ArrayList<>();
        donorTickets.stream().map((donorTicket->{
            User user=userRepository.findById(donorTicket.getUsername()).get();
            return userDtoList.add(UserToDto(user));
        })).collect(Collectors.toList());
        return userDtoList;
    }

    private List<Ticket> getDonorTickets(List<Ticket> tickets) {
        List<Ticket> donorTickets=new ArrayList<>();
        tickets.stream().peek((ticket -> {
            if(ticket.getDonor()){
                donorTickets.add(ticket);
            }
        })).collect(Collectors.toList());
        return donorTickets;
    }

    public User DToToUser(UserDto userDto){
        User user=modelMapper.map(userDto,User.class);
        return user;
    }

    public UserDto UserToDto(User user){
        UserDto userDto=modelMapper.map(user,UserDto.class);
        return userDto;
    }

    public String getEncodedPassword(String password){
        return passwordEncoder.encode(password);
    }

    public String getUsernameFromToken(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
