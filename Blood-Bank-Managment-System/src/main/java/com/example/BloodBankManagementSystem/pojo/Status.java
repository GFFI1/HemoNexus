package com.example.BloodBankManagementSystem.pojo;

import lombok.Data;

@Data
public class Status {
    private Integer statusCode;
    private boolean success;
    private String message;

    public Status() {
    }

    public Status(Integer statusCode,Boolean success, String message) {
        this.statusCode = statusCode;
        this.success = success;
        this.message = message;
    }
}
