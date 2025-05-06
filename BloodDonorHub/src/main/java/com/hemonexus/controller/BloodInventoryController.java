package com.hemonexus.controller;

import com.hemonexus.dto.BloodInventoryDTO;
import com.hemonexus.dto.BloodStockDTO;
import com.hemonexus.dto.MessageResponseDTO;
import com.hemonexus.service.BloodInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/inventory")
public class BloodInventoryController {
    @Autowired
    private BloodInventoryService inventoryService;

    @GetMapping
    public ResponseEntity<List<BloodInventoryDTO>> getAllInventory() {
        List<BloodInventoryDTO> inventory = inventoryService.getAllInventory();
        return ResponseEntity.ok(inventory);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BloodInventoryDTO> getInventoryById(@PathVariable Long id) {
        return inventoryService.getInventoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/blood-bank/{bloodBankId}")
    public ResponseEntity<List<BloodInventoryDTO>> getInventoryByBloodBank(@PathVariable Long bloodBankId) {
        List<BloodInventoryDTO> inventory = inventoryService.getInventoryByBloodBank(bloodBankId);
        return ResponseEntity.ok(inventory);
    }

    @GetMapping("/blood-bank/{bloodBankId}/blood-type/{bloodType}")
    public ResponseEntity<List<BloodInventoryDTO>> getAvailableBloodByType(
            @PathVariable Long bloodBankId,
            @PathVariable String bloodType) {
        List<BloodInventoryDTO> inventory = inventoryService.getAvailableBloodByType(bloodBankId, bloodType);
        return ResponseEntity.ok(inventory);
    }

    @GetMapping("/blood-bank/{bloodBankId}/stock")
    public ResponseEntity<List<BloodStockDTO>> getBloodStockByBloodBank(@PathVariable Long bloodBankId) {
        List<BloodStockDTO> bloodStock = inventoryService.getBloodStockByBloodBank(bloodBankId);
        return ResponseEntity.ok(bloodStock);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'BLOOD_BANK_ADMIN')")
    public ResponseEntity<BloodInventoryDTO> addToInventory(@Valid @RequestBody BloodInventoryDTO inventoryDTO) {
        BloodInventoryDTO newInventory = inventoryService.addToInventory(inventoryDTO);
        return ResponseEntity.ok(newInventory);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'BLOOD_BANK_ADMIN')")
    public ResponseEntity<BloodInventoryDTO> updateInventory(
            @PathVariable Long id,
            @Valid @RequestBody BloodInventoryDTO inventoryDTO) {
        return inventoryService.updateInventory(id, inventoryDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/use")
    @PreAuthorize("hasAnyRole('ADMIN', 'BLOOD_BANK_ADMIN')")
    public ResponseEntity<BloodInventoryDTO> markAsUsed(@PathVariable Long id) {
        return inventoryService.markAsUsed(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteInventory(@PathVariable Long id) {
        boolean deleted = inventoryService.deleteInventory(id);
        if (deleted) {
            return ResponseEntity.ok(new MessageResponseDTO("Inventory item deleted successfully"));
        }
        return ResponseEntity.notFound().build();
    }
}