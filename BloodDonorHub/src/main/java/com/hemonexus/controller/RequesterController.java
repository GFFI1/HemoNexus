package com.hemonexus.controller;

import com.hemonexus.dto.BloodRequestDTO;
import com.hemonexus.service.BloodRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import com.hemonexus.model.BloodRequest; // Adjust the package path if necessary
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/requester")
@RequiredArgsConstructor
@PreAuthorize("hasRole('REQUESTER')")
public class RequesterController {

    private final BloodRequestService svc;

    @GetMapping("/requests")
    public List<BloodRequestDTO> own() {
        return svc.getAll();
    }

    @PostMapping("/requests")
    public BloodRequestDTO create(@RequestBody BloodRequestDTO dto) {
        return svc.create(dto);
    }

    @DeleteMapping("/requests/{id}")
    public void delete(@PathVariable Long id) {
        svc.updateStatus(id, BloodRequest.Status.CANCELLED);
    }
}
