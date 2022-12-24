package com.example.BloodBankManagementSystem.entities;

public enum BloodType {
    /*->in the database these codes will be stored and from the client-side we are expecting the
    APOS,ANEG etc.*/


    APOS("a+ve"),ANEG("a-ve"),BPOS("b+ve"),BNEG("b-ve"),ABPOS("ab+ve"),
    ABNEG("ab-ve"),OPOS("o+ve"),ONEG("o-ve");



    private String code;

    private BloodType(String code){
        this.code=code;
    }

    public String getCode(){
        return code;
    }
}
