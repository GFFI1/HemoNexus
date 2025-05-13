package com.hemonexus.repository;

import com.hemonexus.model.BloodRequest;
import com.hemonexus.model.BloodRequest.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BloodRequestRepository
        extends JpaRepository<BloodRequest, Long> {

    List<BloodRequest> findByStatus(Status status);

    List<BloodRequest> findByRequester_Id(Long requesterId);

}
