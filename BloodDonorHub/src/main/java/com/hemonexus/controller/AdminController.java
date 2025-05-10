package com.hemonexus.controller;

import com.hemonexus.dto.*;
//import com.hemonexus.dto.DonorDTO; // Update to the correct package path if the class is located elsewhere
import com.hemonexus.service.UserService;
import com.hemonexus.model.BloodRequest.Status;
import com.hemonexus.service.BloodRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserService userSvc;
    private final BloodRequestService reqSvc;

    /* ---- Donors ------------------------------------------------------- */
    @GetMapping("/donors")
    public List<DonorDTO> donors() {
        return userSvc.listDonors();
    }

    @PutMapping("/donors/{id}")
    public DonorDTO patchDonor(@PathVariable Long id, @RequestBody Map<String, Object> p) {
        return userSvc.patchDonor(id, p);
    }

    /* ---- Requesters --------------------------------------------------- */
    @GetMapping("/requesters")
    public List<RequesterDTO> requesters() {
        return userSvc.listRequesters();
    }

    @PutMapping("/requesters/{id}")
    public RequesterDTO patchRequester(@PathVariable Long id, @RequestBody Map<String, Object> p) {
        return userSvc.patchRequester(id, p);
    }

    /* ---- Blood requests ---------------------------------------------- */
    @GetMapping("/requests")
    public List<BloodRequestDTO> pending() {
        return reqSvc.findPending();
    }

    @PutMapping("/requests/{id}")
    public BloodRequestDTO changeStatus(@PathVariable Long id,
            @RequestBody StatusDTO dto) {
        Status newStatus = Status.valueOf(dto.getStatus().toUpperCase());
        return reqSvc.updateStatus(id, newStatus); // ← enum not String
    }
}
