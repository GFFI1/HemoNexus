package com.example.BloodBankManagementSystem.configuration;

public class AppConstants {

    //token will be valid for 1hr
    public static final long JWT_TOKEN_VALIDITY=1000*3600;

    //this key will be present in the signature part of jwt
    public static final String SECRET="jwtsecretkey";
}
