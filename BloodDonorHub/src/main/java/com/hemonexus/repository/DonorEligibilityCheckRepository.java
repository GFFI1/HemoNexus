package com.hemonexus.repository;

import com.hemonexus.model.DonorEligibilityCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DonorEligibilityCheckRepository extends JpaRepository<DonorEligibilityCheck, Long> {
    List<DonorEligibilityCheck> findByDonorId(Long donorId);

    @Query("SELECT c FROM DonorEligibilityCheck c WHERE c.donor.id = ?1 ORDER BY c.checkDate DESC")
    List<DonorEligibilityCheck> findLatestChecksByDonorId(Long donorId);

    Optional<DonorEligibilityCheck> findTopByDonorIdOrderByCheckDateDesc(Long donorId);

    @Query("SELECT c FROM DonorEligibilityCheck c WHERE c.nextEligibleDate <= ?1 AND c.isEligible = false")
    List<DonorEligibilityCheck> findNewlyEligibleDonors(LocalDateTime currentDate);
}