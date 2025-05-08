package com.hemonexus.controller;

import com.hemonexus.dto.BloodRequestDTO;
import com.hemonexus.model.BloodRequest.Status;
import com.hemonexus.service.BloodRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blood-requests")
@RequiredArgsConstructor
public class BloodRequestController {
    private final BloodRequestService svc;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<BloodRequestDTO> all() {
        return svc.getAll();
    }

    @PostMapping
    @PreAuthorize("hasRole('REQUESTER')")
    public ResponseEntity<BloodRequestDTO> create(@Valid @RequestBody BloodRequestDTO d) {
        return ResponseEntity.ok(svc.create(d));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BloodRequestDTO> setStatus(
            @PathVariable Long id,
            @RequestParam Status status) {
        return ResponseEntity.ok(svc.updateStatus(id, status));
    }
}
