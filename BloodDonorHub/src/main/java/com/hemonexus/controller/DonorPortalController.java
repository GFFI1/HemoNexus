package com.hemonexus.controller;

import com.hemonexus.dto.DonorDashboardDTO;
import com.hemonexus.repository.DonationRepository;
import com.hemonexus.security.services.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/donor")
@RequiredArgsConstructor
@PreAuthorize("hasRole('DONOR')")
public class DonorPortalController {

    private final DonationRepository donationRepo;

    @GetMapping("/dashboard")
    public DonorDashboardDTO stats(@AuthenticationPrincipal UserDetailsImpl me) {
        long total = donationRepo.countByDonor_User_Id(me.getId()); // fixed

        return new DonorDashboardDTO(total);
    }

}
