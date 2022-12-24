package com.example.BloodBankManagementSystem.security;


import com.example.BloodBankManagementSystem.entities.User;
import com.example.BloodBankManagementSystem.exceptions.NotFoundException;
import com.example.BloodBankManagementSystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    //whenever spring-security wants to load the user this method will be triggered
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(userRepository.findById(username).isPresent()){
            User user=userRepository.findById(username).get();
            return user;
            //return type works fine bcz user entity has implemented UserDetails interfcae
        }
        throw new NotFoundException("User with the username "+username+" is not present");
    }
}
