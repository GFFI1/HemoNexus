package com.example.BloodBankManagementSystem.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class BloodRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;

    @Column(name="blood_type")
    private BloodType bloodType;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Integer bankId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private Date createdOn;

    //initially when the request is created the status will be pending....
    private StatusType statusType;

    //according to the status of the patient the user will send the PriorityType from the client-side
    private PriorityType priorityType;

    @Column(nullable = false)
    private String discription;
}
