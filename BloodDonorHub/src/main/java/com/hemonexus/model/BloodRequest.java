package com.hemonexus.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "blood_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BloodRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "blood_bank_id", nullable = false)
    private BloodBank bloodBank;

    @Column(nullable = false)
    private String patientName;

    private String patientId;
    private Integer patientAge;
    private String patientGender;

    @Column(nullable = false)
    private String bloodType;

    @Column(nullable = false)
    private Double quantityRequested; // in milliliters

    private Double quantityFulfilled = 0.0;

    @Column(nullable = false)
    private LocalDateTime requiredBy;

    @Column(nullable = false)
    private String requesterName;

    private String requesterContact;
    private String hospitalName;
    private String purpose;
    private String urgencyLevel;
    private String notes;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestStatus status = RequestStatus.PENDING;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public enum RequestStatus {
        PENDING, APPROVED, REJECTED, PARTIALLY_FULFILLED, FULFILLED, CANCELLED, EXPIRED
    }
}