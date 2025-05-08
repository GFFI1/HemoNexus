package com.hemonexus.repository;

import com.hemonexus.model.BloodRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BloodRequestRepository extends JpaRepository<BloodRequest, Long> {
    List<BloodRequest> findByBloodBankId(Long bloodBankId);

    Page<BloodRequest> findByStatus(BloodRequest.RequestStatus status, Pageable pageable);

    @Query("SELECT br FROM BloodRequest br WHERE br.bloodType = ?1 AND br.status IN ('PENDING', 'PARTIALLY_FULFILLED') AND br.requiredBy > ?2")
    List<BloodRequest> findActiveRequestsByBloodType(String bloodType, LocalDateTime currentDate);

    @Query("SELECT br FROM BloodRequest br WHERE br.bloodBank.id = ?1 AND br.status IN ?2")
    List<BloodRequest> findByBloodBankAndStatusIn(Long bloodBankId, List<BloodRequest.RequestStatus> statuses);

    @Query("SELECT br FROM BloodRequest br WHERE br.requiredBy BETWEEN ?1 AND ?2")
    List<BloodRequest> findByRequiredByBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT SUM(br.quantityRequested) FROM BloodRequest br WHERE br.bloodType = ?1 AND br.status IN ('PENDING', 'PARTIALLY_FULFILLED')")
    Double getTotalRequestedQuantityByBloodType(String bloodType);
}