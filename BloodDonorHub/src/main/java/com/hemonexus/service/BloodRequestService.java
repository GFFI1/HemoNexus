package com.hemonexus.service;

import com.hemonexus.dto.BloodRequestDTO;
import com.hemonexus.model.BloodRequest;
import com.hemonexus.model.BloodRequest.Status;
import com.hemonexus.model.User;
import com.hemonexus.repository.BloodRequestRepository;
import com.hemonexus.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BloodRequestService {

    private final BloodRequestRepository repo;
    private final UserRepository userRepo;

    /* ---------------- public API ---------------- */

    public List<BloodRequestDTO> getAll() {
        return repo.findAll()
                .stream().map(this::toDTO)
                .collect(Collectors.toList());
    }

    /** returns only requests whose status == PENDING */
    @Transactional(readOnly = true) // <-- IMPLEMENTED
    public List<BloodRequestDTO> findPending() {
        return repo.findByStatus(Status.PENDING)
                .stream().map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public BloodRequestDTO create(BloodRequestDTO dto) {
        User requester = userRepo.findById(dto.getRequesterId())
                .orElseThrow(() -> new RuntimeException("Requester not found"));

        BloodRequest br = new BloodRequest();
        br.setRequester(requester);
        updateEntity(br, dto);
        br.setStatus(Status.PENDING);

        return toDTO(repo.save(br));
    }

    @Transactional
    public BloodRequestDTO updateStatus(Long id, Status s) {
        BloodRequest br = repo.findById(id).orElseThrow();
        br.setStatus(s);
        return toDTO(repo.save(br));
    }

    /* ---------------- helpers ---------------- */

    private void updateEntity(BloodRequest br, BloodRequestDTO d) {
        br.setBloodType(d.getBloodType());
        br.setUnitsNeeded(d.getUnitsNeeded());
        br.setUrgencyLevel(d.getUrgencyLevel());
        br.setHospitalName(d.getHospitalName());
        br.setLocation(d.getLocation());
    }

    private BloodRequestDTO toDTO(BloodRequest b) {
        return new BloodRequestDTO(
                b.getId(),
                b.getRequester().getId(),
                b.getBloodType(),
                b.getUnitsNeeded(),
                b.getUrgencyLevel(),
                b.getStatus(),
                b.getHospitalName(),
                b.getLocation());
    }
}
