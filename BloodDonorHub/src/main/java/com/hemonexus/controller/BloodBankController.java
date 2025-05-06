package com.hemonexus.controller;

import com.hemonexus.dto.BloodBankDTO;
import com.hemonexus.dto.MessageResponseDTO;
import com.hemonexus.service.BloodBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/blood-banks")
public class BloodBankController {
    @Autowired
    private BloodBankService bloodBankService;

    @GetMapping
    public ResponseEntity<List<BloodBankDTO>> getAllBloodBanks() {
        List<BloodBankDTO> bloodBanks = bloodBankService.getAllBloodBanks();
        return ResponseEntity.ok(bloodBanks);
    }

    @GetMapping("/active")
    public ResponseEntity<List<BloodBankDTO>> getActiveBloodBanks() {
        List<BloodBankDTO> bloodBanks = bloodBankService.getActiveBloodBanks();
        return ResponseEntity.ok(bloodBanks);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<BloodBankDTO>> searchActiveBloodBanks(@RequestParam String term, Pageable pageable) {
        Page<BloodBankDTO> bloodBanks = bloodBankService.searchActiveBloodBanks(term, pageable);
        return ResponseEntity.ok(bloodBanks);
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<BloodBankDTO>> getBloodBanksByCity(@PathVariable String city) {
        List<BloodBankDTO> bloodBanks = bloodBankService.getBloodBanksByCity(city);
        return ResponseEntity.ok(bloodBanks);
    }

    @GetMapping("/cities")
    public ResponseEntity<List<String>> getAllCities() {
        List<String> cities = bloodBankService.getAllCities();
        return ResponseEntity.ok(cities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BloodBankDTO> getBloodBankById(@PathVariable Long id) {
        return bloodBankService.getBloodBankById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'BLOOD_BANK_ADMIN')")
    public ResponseEntity<BloodBankDTO> createBloodBank(@Valid @RequestBody BloodBankDTO bloodBankDTO) {
        BloodBankDTO newBloodBank = bloodBankService.createBloodBank(bloodBankDTO);
        return ResponseEntity.ok(newBloodBank);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'BLOOD_BANK_ADMIN')")
    public ResponseEntity<BloodBankDTO> updateBloodBank(@PathVariable Long id,
            @Valid @RequestBody BloodBankDTO bloodBankDTO) {
        return bloodBankService.updateBloodBank(id, bloodBankDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BloodBankDTO> toggleStatus(@PathVariable Long id) {
        return bloodBankService.toggleBloodBankStatus(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteBloodBank(@PathVariable Long id) {
        boolean deleted = bloodBankService.deleteBloodBank(id);
        if (deleted) {
            return ResponseEntity.ok(new MessageResponseDTO("Blood bank deleted successfully"));
        }
        return ResponseEntity.notFound().build();
    }
}