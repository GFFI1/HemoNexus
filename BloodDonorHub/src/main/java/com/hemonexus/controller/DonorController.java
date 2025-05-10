package com.hemonexus.controller;

import com.hemonexus.dto.DonorDTO;
import com.hemonexus.dto.DonationDTO;
import com.hemonexus.dto.MessageResponseDTO;
import com.hemonexus.service.DonorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/donor") // singular, no /api (context-path covers it)
@RequiredArgsConstructor
public class DonorController {

    private final DonorService donorService;

    /* ---------- NEW history endpoint ---------- */
    @GetMapping("/history")
    @PreAuthorize("hasRole('DONOR')")
    public List<DonationDTO> ownHistory() {
        return donorService.listOwnHistory();
    }

    /* ---------- everything you had, unchanged except prefix ---------- */

    @GetMapping
    public ResponseEntity<Page<DonorDTO>> all(Pageable pageable) {
        return ResponseEntity.ok(donorService.getDonorsPaginated(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DonorDTO> get(@PathVariable Long id) {
        return donorService.getDonorById(id)
                .map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/blood-type/{bloodType}")
    public ResponseEntity<List<DonorDTO>> byType(@PathVariable String bloodType) {
        return ResponseEntity.ok(donorService.getDonorsByBloodType(bloodType));
    }

    @GetMapping("/eligible/blood-type/{bloodType}")
    public ResponseEntity<List<DonorDTO>> eligible(@PathVariable String bloodType) {
        return ResponseEntity.ok(donorService.getEligibleDonorsByBloodType(bloodType));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<DonorDTO>> search(@RequestParam String term, Pageable p) {
        return ResponseEntity.ok(donorService.searchDonors(term, p));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DonorDTO> create(@Valid @RequestBody DonorDTO d) {
        return ResponseEntity.ok(donorService.createDonor(d));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DonorDTO> update(@PathVariable Long id, @Valid @RequestBody DonorDTO d) {
        return donorService.updateDonor(id, d)
                .map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return donorService.deleteDonor(id) ? ResponseEntity.ok(new MessageResponseDTO("Deleted"))
                : ResponseEntity.notFound().build();
    }
}
