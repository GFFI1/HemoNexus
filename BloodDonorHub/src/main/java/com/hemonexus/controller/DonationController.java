package com.hemonexus.controller;

import com.hemonexus.dto.DonationDTO;
import com.hemonexus.dto.MessageResponseDTO;
import com.hemonexus.model.Donation;
import com.hemonexus.service.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/donations")
public class DonationController {
    @Autowired
    private DonationService donationService;

    @GetMapping
    public ResponseEntity<Page<DonationDTO>> getAllDonations(Pageable pageable) {
        Page<DonationDTO> donations = donationService.getDonationsPaginated(pageable);
        return ResponseEntity.ok(donations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DonationDTO> getDonationById(@PathVariable Long id) {
        return donationService.getDonationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/donor/{donorId}")
    public ResponseEntity<List<DonationDTO>> getDonationsByDonorId(@PathVariable Long donorId) {
        List<DonationDTO> donations = donationService.getDonationsByDonorId(donorId);
        return ResponseEntity.ok(donations);
    }

    @GetMapping("/blood-bank/{bloodBankId}")
    public ResponseEntity<List<DonationDTO>> getDonationsByBloodBankId(@PathVariable Long bloodBankId) {
        List<DonationDTO> donations = donationService.getDonationsByBloodBankId(bloodBankId);
        return ResponseEntity.ok(donations);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<DonationDTO>> getDonationsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<DonationDTO> donations = donationService.getDonationsByDateRange(startDate, endDate);
        return ResponseEntity.ok(donations);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Page<DonationDTO>> getDonationsByStatus(
            @PathVariable Donation.DonationStatus status, Pageable pageable) {
        Page<DonationDTO> donations = donationService.getDonationsByStatus(status, pageable);
        return ResponseEntity.ok(donations);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'BLOOD_BANK_ADMIN')")
    public ResponseEntity<DonationDTO> createDonation(@Valid @RequestBody DonationDTO donationDTO) {
        DonationDTO newDonation = donationService.createDonation(donationDTO);
        return ResponseEntity.ok(newDonation);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'BLOOD_BANK_ADMIN')")
    public ResponseEntity<DonationDTO> updateDonation(@PathVariable Long id,
            @Valid @RequestBody DonationDTO donationDTO) {
        return donationService.updateDonation(id, donationDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'BLOOD_BANK_ADMIN')")
    public ResponseEntity<DonationDTO> updateDonationStatus(
            @PathVariable Long id,
            @RequestParam Donation.DonationStatus status) {
        return donationService.updateDonationStatus(id, status)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteDonation(@PathVariable Long id) {
        boolean deleted = donationService.deleteDonation(id);
        if (deleted) {
            return ResponseEntity.ok(new MessageResponseDTO("Donation deleted successfully"));
        }
        return ResponseEntity.notFound().build();
    }
}