package com.example.BloodBankManagementSystem.services;

import com.example.BloodBankManagementSystem.payload.UserDto;

import java.util.List;

public interface UserService {

    public void initialConfiguration();
    public UserDto createUser(UserDto userDto);
    public UserDto updateUser(UserDto userDto,String username);
    public void deleteUser(String username);
    public UserDto getUserById(String username);
    public List<UserDto> getAllUsers();
    public UserDto getLoggedUser();
    public List<UserDto> getAllDonors();
}
