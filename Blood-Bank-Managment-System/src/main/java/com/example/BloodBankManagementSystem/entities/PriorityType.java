package com.example.BloodBankManagementSystem.entities;

public enum PriorityType {

    HIGH("high"),MEDIUM("medium"),LOW("low");



    private String code;

    private PriorityType(String code){
        this.code=code;
    }

    public String getCode(){
        return code;
    }
}
