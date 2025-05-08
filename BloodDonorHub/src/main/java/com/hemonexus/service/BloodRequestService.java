package com.hemonexus.service;

import com.hemonexus.dto.BloodRequestDTO;
import com.hemonexus.model.BloodRequest;
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

    public List<BloodRequestDTO> getAll() {
        return repo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public BloodRequestDTO create(BloodRequestDTO dto) {
        BloodRequest br = new BloodRequest();
        User r = userRepo.findById(dto.getRequesterId())
                .orElseThrow(() -> new RuntimeException("Requester not found"));
        br.setRequester(r);
        update(br, dto);
        return toDTO(repo.save(br));
    }

    @Transactional
    public BloodRequestDTO updateStatus(Long id, BloodRequest.Status s) {
        BloodRequest br = repo.findById(id).orElseThrow();
        br.setStatus(s);
        return toDTO(repo.save(br));
    }

    /* ---------------- helpers ---------------- */
    private void update(BloodRequest br, BloodRequestDTO d) {
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
