package com.hemonexus.repository;

import com.hemonexus.model.Donation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.hemonexus.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {
    List<Donation> findByDonorId(Long donorId);

    List<Donation> findByDonor_User_Id(Long userId);

    List<Donation> findByBloodBankId(Long bloodBankId);

    long countByDonor_User(User user);

    long countByDonor_User_Id(Long userId);

    @Query("SELECT d FROM Donation d WHERE d.donationDate BETWEEN ?1 AND ?2")
    List<Donation> findByDonationDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    Page<Donation> findByStatus(Donation.DonationStatus status, Pageable pageable);

    @Query("""
            select coalesce(sum(d.quantity), 0)
            from   Donation d
            where  d.status = com.hemonexus.model.Donation.DonationStatus.COMPLETED
            """)
    long sumQuantityByStatusCompleted();

    @Query("SELECT d FROM Donation d WHERE d.bloodBank.id = ?1 AND d.status = ?2")
    List<Donation> findByBloodBankAndStatus(Long bloodBankId, Donation.DonationStatus status);

    @Query("SELECT SUM(d.quantity) FROM Donation d WHERE d.bloodType = ?1 AND d.status = 'COMPLETED'")
    Double getTotalDonationQuantityByBloodType(String bloodType);
}