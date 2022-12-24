package com.example.BloodBankManagementSystem.pojo;

import lombok.Data;

//this is what we will send to the client-side
@Data
public class Response<T> {

    private T body; //it will work for all the types..
    private Status status;

    public Response(){

    }
    public Response(T body,Status status){
        this.body=body;
        this.status=status;
    }


}
