package com.hemonexus.service;

import com.hemonexus.dto.BloodInventoryDTO;
import com.hemonexus.dto.BloodStockDTO;
import com.hemonexus.model.BloodBank;
import com.hemonexus.model.BloodInventory;
import com.hemonexus.model.Donation;
import com.hemonexus.repository.BloodBankRepository;
import com.hemonexus.repository.BloodInventoryRepository;
import com.hemonexus.repository.DonationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BloodInventoryService {
    @Autowired
    private BloodInventoryRepository bloodInventoryRepository;

    @Autowired
    private BloodBankRepository bloodBankRepository;

    @Autowired
    private DonationRepository donationRepository;

    public List<BloodInventoryDTO> getAllInventory() {
        return bloodInventoryRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<BloodInventoryDTO> getInventoryById(Long id) {
        return bloodInventoryRepository.findById(id)
                .map(this::convertToDTO);
    }

    public List<BloodInventoryDTO> getInventoryByBloodBank(Long bloodBankId) {
        return bloodInventoryRepository.findByBloodBankId(bloodBankId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<BloodInventoryDTO> getAvailableBloodByType(Long bloodBankId, String bloodType) {
        return bloodInventoryRepository
                .findAvailableBloodByBloodBankAndType(bloodBankId, bloodType, LocalDateTime.now()).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<BloodStockDTO> getBloodStockByBloodBank(Long bloodBankId) {
        List<Object[]> results = bloodInventoryRepository.getAvailableBloodStockByBloodBank(bloodBankId,
                LocalDateTime.now());
        List<BloodStockDTO> bloodStockList = new ArrayList<>();

        for (Object[] result : results) {
            BloodStockDTO stockDTO = new BloodStockDTO();
            stockDTO.setBloodType((String) result[0]);
            stockDTO.setQuantity((Double) result[1]);
            bloodStockList.add(stockDTO);
        }

        return bloodStockList;
    }

    @Transactional
    public BloodInventoryDTO addToInventory(BloodInventoryDTO inventoryDTO) {
        BloodInventory inventory = new BloodInventory();

        Optional<BloodBank> bloodBankOpt = bloodBankRepository.findById(inventoryDTO.getBloodBankId());
        if (!bloodBankOpt.isPresent()) {
            throw new RuntimeException("Blood Bank not found");
        }
        inventory.setBloodBank(bloodBankOpt.get());

        if (inventoryDTO.getDonationId() != null) {
            Optional<Donation> donationOpt = donationRepository.findById(inventoryDTO.getDonationId());
            donationOpt.ifPresent(inventory::setDonation);
        }

        updateInventoryFromDTO(inventory, inventoryDTO);
        BloodInventory savedInventory = bloodInventoryRepository.save(inventory);
        return convertToDTO(savedInventory);
    }

    @Transactional
    public Optional<BloodInventoryDTO> updateInventory(Long id, BloodInventoryDTO inventoryDTO) {
        return bloodInventoryRepository.findById(id)
                .map(inventory -> {
                    updateInventoryFromDTO(inventory, inventoryDTO);
                    return convertToDTO(bloodInventoryRepository.save(inventory));
                });
    }

    @Transactional
    public Optional<BloodInventoryDTO> markAsUsed(Long id) {
        return bloodInventoryRepository.findById(id)
                .map(inventory -> {
                    inventory.setIsAvailable(false);
                    return convertToDTO(bloodInventoryRepository.save(inventory));
                });
    }

    @Scheduled(cron = "0 0 0 * * *") // Run at midnight every day
    @Transactional
    public void checkExpiringInventory() {
        LocalDateTime expirationDate = LocalDateTime.now().plusDays(7); // Get items expiring in 7 days
        List<BloodInventory> expiringItems = bloodInventoryRepository.findExpiringInventory(expirationDate);

        // Here you would trigger notifications or generate reports
        // For this example, we'll just mark very close to expiration items (1 day)
        LocalDateTime oneDayExpiration = LocalDateTime.now().plusDays(1);
        for (BloodInventory item : expiringItems) {
            if (item.getExpirationDate().isBefore(oneDayExpiration)) {
                // Consider special handling for very close to expiration
            }
        }
    }

    @Transactional
    public boolean deleteInventory(Long id) {
        return bloodInventoryRepository.findById(id)
                .map(inventory -> {
                    bloodInventoryRepository.delete(inventory);
                    return true;
                })
                .orElse(false);
    }

    private void updateInventoryFromDTO(BloodInventory inventory, BloodInventoryDTO dto) {
        inventory.setBloodType(dto.getBloodType());
        inventory.setQuantity(dto.getQuantity());
        inventory.setExpirationDate(dto.getExpirationDate());
        inventory.setStorageLocation(dto.getStorageLocation());
        inventory.setBatchNumber(dto.getBatchNumber());
        inventory.setIsAvailable(dto.getIsAvailable());
    }

    private BloodInventoryDTO convertToDTO(BloodInventory inventory) {
        BloodInventoryDTO dto = new BloodInventoryDTO();
        dto.setId(inventory.getId());
        dto.setBloodBankId(inventory.getBloodBank().getId());
        dto.setBloodBankName(inventory.getBloodBank().getName());
        dto.setBloodType(inventory.getBloodType());
        dto.setQuantity(inventory.getQuantity());
        dto.setExpirationDate(inventory.getExpirationDate());

        if (inventory.getDonation() != null) {
            dto.setDonationId(inventory.getDonation().getId());
        }

        dto.setStorageLocation(inventory.getStorageLocation());
        dto.setBatchNumber(inventory.getBatchNumber());
        dto.setIsAvailable(inventory.getIsAvailable());
        dto.setCreatedAt(inventory.getCreatedAt());
        dto.setUpdatedAt(inventory.getUpdatedAt());
        return dto;
    }
}