package com.hemonexus.controller;

import com.hemonexus.dto.*;
import com.hemonexus.model.BloodRequest.Status;
import com.hemonexus.service.UserService;
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

    /* donors ------------------------------------------------------ */
    @GetMapping("/donors")
    public List<DonorDTO> donors() {
        return userSvc.listDonors();
    }

    @PatchMapping("/donors/{id}")
    public DonorDTO patchDonor(@PathVariable Long id, @RequestBody DonorDTO dto) {
        return userSvc.patchDonor(id, Map.of(
                "firstName", dto.getFirstName(),
                "bloodType", dto.getBloodType(),
                "city", dto.getCity()));
    }

    /* requesters -------------------------------------------------- */
    @GetMapping("/requesters")
    public List<RequesterDTO> requesters() {
        return userSvc.listRequesters();
    }

    @PatchMapping("/requesters/{id}")
    public RequesterDTO patchRequester(@PathVariable Long id, @RequestBody RequesterDTO dto) {
        return userSvc.patchRequester(id, Map.of("city", dto.getCity()));
    }

    /* blood requests --------------------------------------------- */
    @GetMapping("/requests")
    public List<BloodRequestDTO> pending() {
        return reqSvc.findPending();
    }

    @PutMapping("/requests/{id}")
    public BloodRequestDTO changeStatus(@PathVariable Long id, @RequestBody StatusDTO dto) {
        Status s = Status.valueOf(dto.getStatus().toUpperCase());
        return reqSvc.updateStatus(id, s);
    }

    /* blood banks ------------------------------------------------- */
    @PostMapping("/bloodbanks")
    public BloodBankDTO createBank(@RequestBody BloodBankDTO dto) {
        return userSvc.createBank(dto);
    }
}
