package com.example.BloodBankManagementSystem.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Configuration //so that we can create the Beans
public class ModelMapperConfig {

    @Bean //we can directly auto-wire it anywhere
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }


}
