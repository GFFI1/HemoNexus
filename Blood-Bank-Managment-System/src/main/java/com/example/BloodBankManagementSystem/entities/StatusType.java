package com.example.BloodBankManagementSystem.entities;

public enum StatusType {



    APPROVE("approve"),TRANSFER("transfer"),DECLINE("decline"),PENDING("pending");



    private String code;

    private StatusType(String code){
        this.code=code;
    }

    public String getCode(){
        return code;
    }
}
