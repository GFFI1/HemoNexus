package com.hemonexus.controller;

import com.hemonexus.dto.DonorDTO;
import com.hemonexus.dto.MessageResponseDTO;
import com.hemonexus.service.DonorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/donors")
public class DonorController {
    @Autowired
    private DonorService donorService;

    @GetMapping
    public ResponseEntity<Page<DonorDTO>> getAllDonors(Pageable pageable) {
        Page<DonorDTO> donors = donorService.getDonorsPaginated(pageable);
        return ResponseEntity.ok(donors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DonorDTO> getDonorById(@PathVariable Long id) {
        return donorService.getDonorById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/blood-type/{bloodType}")
    public ResponseEntity<List<DonorDTO>> getDonorsByBloodType(@PathVariable String bloodType) {
        List<DonorDTO> donors = donorService.getDonorsByBloodType(bloodType);
        return ResponseEntity.ok(donors);
    }

    @GetMapping("/eligible/blood-type/{bloodType}")
    public ResponseEntity<List<DonorDTO>> getEligibleDonorsByBloodType(@PathVariable String bloodType) {
        List<DonorDTO> donors = donorService.getEligibleDonorsByBloodType(bloodType);
        return ResponseEntity.ok(donors);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<DonorDTO>> searchDonors(@RequestParam String term, Pageable pageable) {
        Page<DonorDTO> donors = donorService.searchDonors(term, pageable);
        return ResponseEntity.ok(donors);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'BLOOD_BANK_ADMIN')")
    public ResponseEntity<DonorDTO> createDonor(@Valid @RequestBody DonorDTO donorDTO) {
        DonorDTO newDonor = donorService.createDonor(donorDTO);
        return ResponseEntity.ok(newDonor);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'BLOOD_BANK_ADMIN')")
    public ResponseEntity<DonorDTO> updateDonor(@PathVariable Long id, @Valid @RequestBody DonorDTO donorDTO) {
        return donorService.updateDonor(id, donorDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/eligibility")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'BLOOD_BANK_ADMIN')")
    public ResponseEntity<DonorDTO> updateEligibility(
            @PathVariable Long id,
            @RequestParam Boolean isEligible,
            @RequestParam(required = false) String medicalNotes) {
        return donorService.updateDonorEligibility(id, isEligible, medicalNotes)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteDonor(@PathVariable Long id) {
        boolean deleted = donorService.deleteDonor(id);
        if (deleted) {
            return ResponseEntity.ok(new MessageResponseDTO("Donor deleted successfully"));
        }
        return ResponseEntity.notFound().build();
    }
}