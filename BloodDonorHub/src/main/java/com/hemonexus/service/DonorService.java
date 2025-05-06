package com.hemonexus.service;

import com.hemonexus.dto.DonorDTO;
import com.hemonexus.model.Donor;
import com.hemonexus.model.User;
import com.hemonexus.repository.DonorRepository;
import com.hemonexus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DonorService {
    @Autowired
    private DonorRepository donorRepository;

    @Autowired
    private UserRepository userRepository;

    public List<DonorDTO> getAllDonors() {
        return donorRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Page<DonorDTO> getDonorsPaginated(Pageable pageable) {
        return donorRepository.findAll(pageable)
                .map(this::convertToDTO);
    }

    public Optional<DonorDTO> getDonorById(Long id) {
        return donorRepository.findById(id)
                .map(this::convertToDTO);
    }

    public List<DonorDTO> getDonorsByBloodType(String bloodType) {
        return donorRepository.findByBloodType(bloodType).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<DonorDTO> getEligibleDonorsByBloodType(String bloodType) {
        LocalDate minDate = LocalDate.now().minusMonths(3); // Assuming donors are eligible after 3 months
        return donorRepository.findEligibleDonorsByBloodType(bloodType, minDate).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Page<DonorDTO> searchDonors(String searchTerm, Pageable pageable) {
        return donorRepository.searchDonors(searchTerm, pageable)
                .map(this::convertToDTO);
    }

    @Transactional
    public DonorDTO createDonor(DonorDTO donorDTO) {
        Donor donor = new Donor();
        updateDonorFromDTO(donor, donorDTO);

        if (donorDTO.getUserId() != null) {
            Optional<User> userOpt = userRepository.findById(donorDTO.getUserId());
            userOpt.ifPresent(donor::setUser);
        }

        Donor savedDonor = donorRepository.save(donor);
        return convertToDTO(savedDonor);
    }

    @Transactional
    public Optional<DonorDTO> updateDonor(Long id, DonorDTO donorDTO) {
        return donorRepository.findById(id)
                .map(donor -> {
                    updateDonorFromDTO(donor, donorDTO);

                    if (donorDTO.getUserId() != null &&
                            (donor.getUser() == null || !donor.getUser().getId().equals(donorDTO.getUserId()))) {
                        Optional<User> userOpt = userRepository.findById(donorDTO.getUserId());
                        userOpt.ifPresent(donor::setUser);
                    }

                    return convertToDTO(donorRepository.save(donor));
                });
    }

    @Transactional
    public Optional<DonorDTO> updateDonorEligibility(Long id, boolean isEligible, String medicalNotes) {
        return donorRepository.findById(id)
                .map(donor -> {
                    donor.setIsEligible(isEligible);
                    donor.setMedicalNotes(medicalNotes);
                    return convertToDTO(donorRepository.save(donor));
                });
    }

    @Transactional
    public boolean deleteDonor(Long id) {
        return donorRepository.findById(id)
                .map(donor -> {
                    donorRepository.delete(donor);
                    return true;
                })
                .orElse(false);
    }

    private void updateDonorFromDTO(Donor donor, DonorDTO dto) {
        donor.setFirstName(dto.getFirstName());
        donor.setLastName(dto.getLastName());
        donor.setEmail(dto.getEmail());
        donor.setPhoneNumber(dto.getPhoneNumber());
        donor.setDateOfBirth(dto.getDateOfBirth());
        donor.setGender(dto.getGender());
        donor.setBloodType(dto.getBloodType());
        donor.setWeight(dto.getWeight());
        donor.setAddress(dto.getAddress());
        donor.setCity(dto.getCity());
        donor.setState(dto.getState());
        donor.setZipCode(dto.getZipCode());
        donor.setCountry(dto.getCountry());
        donor.setIsEligible(dto.getIsEligible());
        donor.setMedicalNotes(dto.getMedicalNotes());
        donor.setLastDonationDate(dto.getLastDonationDate());
        donor.setTotalDonations(dto.getTotalDonations());
    }

    private DonorDTO convertToDTO(Donor donor) {
        DonorDTO dto = new DonorDTO();
        dto.setId(donor.getId());
        dto.setFirstName(donor.getFirstName());
        dto.setLastName(donor.getLastName());
        dto.setEmail(donor.getEmail());
        dto.setPhoneNumber(donor.getPhoneNumber());
        dto.setDateOfBirth(donor.getDateOfBirth());
        dto.setGender(donor.getGender());
        dto.setBloodType(donor.getBloodType());
        dto.setWeight(donor.getWeight());
        dto.setAddress(donor.getAddress());
        dto.setCity(donor.getCity());
        dto.setState(donor.getState());
        dto.setZipCode(donor.getZipCode());
        dto.setCountry(donor.getCountry());
        dto.setIsEligible(donor.getIsEligible());
        dto.setMedicalNotes(donor.getMedicalNotes());
        dto.setLastDonationDate(donor.getLastDonationDate());
        dto.setTotalDonations(donor.getTotalDonations());

        if (donor.getUser() != null) {
            dto.setUserId(donor.getUser().getId());
        }

        dto.setCreatedAt(donor.getCreatedAt());
        dto.setUpdatedAt(donor.getUpdatedAt());
        return dto;
    }
}