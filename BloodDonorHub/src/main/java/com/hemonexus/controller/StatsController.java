package com.hemonexus.controller;

import com.hemonexus.dto.StatsDTO;
import com.hemonexus.repository.BloodBankRepository;
import com.hemonexus.repository.DonationRepository;
import com.hemonexus.repository.UserRepository;
import com.hemonexus.model.ERole;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("") // same prefix the front-end uses
@RequiredArgsConstructor
public class StatsController {

    private final UserRepository userRepo;
    private final BloodBankRepository bankRepo;
    private final DonationRepository donationRepo;

    @GetMapping("/stats")
    public StatsDTO stats() {
        long donors = userRepo.countByRoles_Name(ERole.ROLE_DONOR);
        long banks = bankRepo.count();
        long units = donationRepo.sumQuantityByStatusCompleted();
        return new StatsDTO(donors, banks, units);
    }
}
