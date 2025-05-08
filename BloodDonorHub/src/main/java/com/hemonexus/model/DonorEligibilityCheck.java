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
@Table(name = "donor_eligibility_checks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class DonorEligibilityCheck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "donor_id", nullable = false)
    private Donor donor;

    @Column(nullable = false)
    private LocalDateTime checkDate;

    @Column(nullable = false)
    private Boolean isEligible;

    private String hemoglobinLevel;
    private String bloodPressure;
    private String pulse;
    private String weight;
    private String temperature;
    private Boolean hasRecentIllness = false;
    private Boolean hasRecentSurgery = false;
    private Boolean hasTattoo = false;
    private LocalDateTime nextEligibleDate;
    private String notes;
    private String checkedBy;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}