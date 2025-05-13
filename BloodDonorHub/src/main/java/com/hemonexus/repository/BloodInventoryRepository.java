package com.hemonexus.repository;

import com.hemonexus.model.BloodInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BloodInventoryRepository extends JpaRepository<BloodInventory, Long> {
    List<BloodInventory> findByBloodBankId(Long bloodBankId);

    @Query("SELECT bi FROM BloodInventory bi WHERE bi.bloodBank.id = ?1 AND bi.bloodType = ?2 AND bi.isAvailable = true AND bi.expirationDate > ?3")
    List<BloodInventory> findAvailableBloodByBloodBankAndType(Long bloodBankId, String bloodType,
            LocalDateTime currentDate);

    @Query("SELECT bi.bloodType, SUM(bi.quantity) FROM BloodInventory bi WHERE bi.bloodBank.id = ?1 AND bi.isAvailable = true AND bi.expirationDate > ?2 GROUP BY bi.bloodType")
    List<Object[]> getAvailableBloodStockByBloodBank(Long bloodBankId, LocalDateTime currentDate);

    @Query("SELECT bi FROM BloodInventory bi WHERE bi.expirationDate < ?1 AND bi.isAvailable = true")
    List<BloodInventory> findExpiringInventory(LocalDateTime expirationDate);

    @Query("SELECT SUM(bi.quantity) FROM BloodInventory bi WHERE bi.bloodType = ?1 AND bi.isAvailable = true AND bi.expirationDate > ?2")
    Double getTotalAvailableQuantityByBloodType(String bloodType, LocalDateTime currentDate);
}