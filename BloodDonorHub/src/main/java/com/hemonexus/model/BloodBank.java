package com.hemonexus.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "blood_banks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BloodBank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    private String city;
    private String state;
    private String zipCode;
    private String country;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String email;

    private String website;
    private String licenseNumber;
    private String description;
    private Boolean isActive = true;

    @OneToMany(mappedBy = "bloodBank", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BloodInventory> inventory = new ArrayList<>();

    @OneToMany(mappedBy = "bloodBank", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Donation> donations = new ArrayList<>();

    // src/main/java/com/hemonexus/model/BloodBank.java
    @OneToMany(mappedBy = "bloodBank", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BloodRequest> bloodRequests = new ArrayList<>();

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}