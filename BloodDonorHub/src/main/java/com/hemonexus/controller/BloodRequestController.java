package com.hemonexus.controller;

import com.hemonexus.dto.BloodRequestDTO;
import com.hemonexus.security.services.UserDetailsImpl;
import com.hemonexus.service.BloodRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * End-points that a REQUESTER (hospital / individual) uses.
 * The ADMIN endpoints for approving / rejecting live in AdminController.
 */
@RestController
@RequestMapping("/api/requester")
@RequiredArgsConstructor
@PreAuthorize("hasRole('REQUESTER')")
public class BloodRequestController {

  private final BloodRequestService svc;

  /** list THIS requester’s own requests (optional for your UI) */
  @GetMapping("/requests")
  public List<BloodRequestDTO> myRequests(@AuthenticationPrincipal UserDetailsImpl me) {
    return svc.findByRequester(me.getId()); // you already have this in the service
  }

  /** create a new blood request */
  @PostMapping("/requests")
  public ResponseEntity<BloodRequestDTO> create(
      @Valid @RequestBody BloodRequestDTO dto,
      @AuthenticationPrincipal UserDetailsImpl me) {

    dto.setRequesterId(me.getId()); // link to logged-in user
    BloodRequestDTO saved = svc.create(dto);
    return ResponseEntity.status(201).body(saved);
  }
}
