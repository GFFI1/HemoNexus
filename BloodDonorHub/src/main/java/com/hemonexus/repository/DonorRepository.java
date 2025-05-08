package com.hemonexus.repository;

import com.hemonexus.model.Donor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DonorRepository extends JpaRepository<Donor, Long> {
    Optional<Donor> findByEmail(String email);

    @Query("SELECT d FROM Donor d WHERE d.isEligible = true")
    Page<Donor> findAllEligibleDonors(Pageable pageable);

    List<Donor> findByBloodType(String bloodType);

    @Query("SELECT d FROM Donor d WHERE d.bloodType = ?1 AND d.isEligible = true AND (d.lastDonationDate IS NULL OR d.lastDonationDate < ?2)")
    List<Donor> findEligibleDonorsByBloodType(String bloodType, LocalDate minDate);

    List<Donor> findByLastDonationDateAfter(LocalDate date);

    @Query("SELECT d FROM Donor d WHERE LOWER(d.firstName) LIKE LOWER(CONCAT('%', ?1, '%')) OR LOWER(d.lastName) LIKE LOWER(CONCAT('%', ?1, '%')) OR LOWER(d.email) LIKE LOWER(CONCAT('%', ?1, '%'))")
    Page<Donor> searchDonors(String searchTerm, Pageable pageable);
}