package com.hemonexus.repository;

import com.hemonexus.model.BloodBank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BloodBankRepository extends JpaRepository<BloodBank, Long> {
    List<BloodBank> findByIsActiveTrue();

    @Query("SELECT bb FROM BloodBank bb WHERE bb.isActive = true AND (LOWER(bb.name) LIKE LOWER(CONCAT('%', ?1, '%')) OR LOWER(bb.city) LIKE LOWER(CONCAT('%', ?1, '%')))")
    Page<BloodBank> searchActiveBloodBanks(String searchTerm, Pageable pageable);

    List<BloodBank> findByCity(String city);

    @Query("SELECT DISTINCT bb.city FROM BloodBank bb WHERE bb.isActive = true ORDER BY bb.city")
    List<String> findAllCities();
}