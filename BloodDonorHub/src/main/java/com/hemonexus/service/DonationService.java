package com.hemonexus.service;

import com.hemonexus.dto.DonationDTO;
import com.hemonexus.model.BloodBank;
import com.hemonexus.model.Donation;
import com.hemonexus.model.Donor;
import com.hemonexus.repository.BloodBankRepository;
import com.hemonexus.repository.DonationRepository;
import com.hemonexus.repository.DonorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DonationService {
    @Autowired
    private DonationRepository donationRepository;

    @Autowired
    private DonorRepository donorRepository;

    @Autowired
    private BloodBankRepository bloodBankRepository;

    public List<DonationDTO> getAllDonations() {
        return donationRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Page<DonationDTO> getDonationsPaginated(Pageable pageable) {
        return donationRepository.findAll(pageable)
                .map(this::convertToDTO);
    }

    public Optional<DonationDTO> getDonationById(Long id) {
        return donationRepository.findById(id)
                .map(this::convertToDTO);
    }

    public List<DonationDTO> getDonationsByDonorId(Long donorId) {
        return donationRepository.findByDonorId(donorId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<DonationDTO> getDonationsByBloodBankId(Long bloodBankId) {
        return donationRepository.findByBloodBankId(bloodBankId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<DonationDTO> getDonationsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return donationRepository.findByDonationDateBetween(startDate, endDate).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Page<DonationDTO> getDonationsByStatus(Donation.DonationStatus status, Pageable pageable) {
        return donationRepository.findByStatus(status, pageable)
                .map(this::convertToDTO);
    }

    @Transactional
    public DonationDTO createDonation(DonationDTO donationDTO) {
        Donation donation = new Donation();

        Optional<Donor> donorOpt = donorRepository.findById(donationDTO.getDonorId());
        if (!donorOpt.isPresent()) {
            throw new RuntimeException("Donor not found");
        }
        donation.setDonor(donorOpt.get());

        Optional<BloodBank> bloodBankOpt = bloodBankRepository.findById(donationDTO.getBloodBankId());
        if (!bloodBankOpt.isPresent()) {
            throw new RuntimeException("Blood Bank not found");
        }
        donation.setBloodBank(bloodBankOpt.get());

        updateDonationFromDTO(donation, donationDTO);
        Donation savedDonation = donationRepository.save(donation);

        // Update donor's last donation date and increment total donations
        Donor donor = donorOpt.get();
        donor.setLastDonationDate(donation.getDonationDate().toLocalDate());
        donor.setTotalDonations(donor.getTotalDonations() + 1);
        donorRepository.save(donor);

        return convertToDTO(savedDonation);
    }

    @Transactional
    public Optional<DonationDTO> updateDonation(Long id, DonationDTO donationDTO) {
        return donationRepository.findById(id)
                .map(donation -> {
                    updateDonationFromDTO(donation, donationDTO);
                    return convertToDTO(donationRepository.save(donation));
                });
    }

    @Transactional
    public Optional<DonationDTO> updateDonationStatus(Long id, Donation.DonationStatus status) {
        return donationRepository.findById(id)
                .map(donation -> {
                    donation.setStatus(status);
                    return convertToDTO(donationRepository.save(donation));
                });
    }

    @Transactional
    public boolean deleteDonation(Long id) {
        return donationRepository.findById(id)
                .map(donation -> {
                    donationRepository.delete(donation);
                    return true;
                })
                .orElse(false);
    }

    private void updateDonationFromDTO(Donation donation, DonationDTO dto) {
        donation.setDonationDate(dto.getDonationDate());

        // Use quantity instead of quantityML
        donation.setQuantity(dto.getQuantityML().doubleValue());

        donation.setBloodType(dto.getBloodType());

        // Convert String to Enum safely
        if (dto.getStatus() != null) {
            try {
                donation.setStatus(Donation.DonationStatus.valueOf(dto.getStatus()));
            } catch (IllegalArgumentException e) {
                // Default to PENDING if invalid status
                donation.setStatus(Donation.DonationStatus.PENDING);
            }
        }

        donation.setHemoglobinLevel(dto.getHemoglobinLevel());
        donation.setPulseRate(dto.getPulseRate());
        donation.setBloodPressure(dto.getBloodPressure());
        donation.setBodyTemperature(dto.getBodyTemperature());
        donation.setNotes(dto.getNotes());
    }

    private DonationDTO convertToDTO(Donation donation) {
        DonationDTO dto = new DonationDTO();
        dto.setId(donation.getId());
        dto.setDonorId(donation.getDonor().getId());
        dto.setDonorName(donation.getDonor().getFirstName() + " " + donation.getDonor().getLastName());
        dto.setBloodBankId(donation.getBloodBank().getId());
        dto.setBloodBankName(donation.getBloodBank().getName());
        dto.setDonationDate(donation.getDonationDate());

        // Convert quantity to quantityML
        dto.setQuantityML((int) donation.getQuantity().doubleValue());

        dto.setBloodType(donation.getBloodType());

        // Convert Enum to String
        if (donation.getStatus() != null) {
            dto.setStatus(donation.getStatus().name());
        }

        dto.setHemoglobinLevel(donation.getHemoglobinLevel());
        dto.setPulseRate(donation.getPulseRate());
        dto.setBloodPressure(donation.getBloodPressure());
        dto.setBodyTemperature(donation.getBodyTemperature());
        dto.setNotes(donation.getNotes());
        dto.setCreatedAt(donation.getCreatedAt());
        dto.setUpdatedAt(donation.getUpdatedAt());
        return dto;
    }
}