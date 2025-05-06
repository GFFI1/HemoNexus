package com.hemonexus.service;

import com.hemonexus.dto.BloodBankDTO;
import com.hemonexus.model.BloodBank;
import com.hemonexus.repository.BloodBankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BloodBankService {
    @Autowired
    private BloodBankRepository bloodBankRepository;

    public List<BloodBankDTO> getAllBloodBanks() {
        return bloodBankRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<BloodBankDTO> getActiveBloodBanks() {
        return bloodBankRepository.findByIsActiveTrue().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Page<BloodBankDTO> searchActiveBloodBanks(String searchTerm, Pageable pageable) {
        return bloodBankRepository.searchActiveBloodBanks(searchTerm, pageable)
                .map(this::convertToDTO);
    }

    public List<BloodBankDTO> getBloodBanksByCity(String city) {
        return bloodBankRepository.findByCity(city).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<String> getAllCities() {
        return bloodBankRepository.findAllCities();
    }

    public Optional<BloodBankDTO> getBloodBankById(Long id) {
        return bloodBankRepository.findById(id)
                .map(this::convertToDTO);
    }

    @Transactional
    public BloodBankDTO createBloodBank(BloodBankDTO bloodBankDTO) {
        BloodBank bloodBank = new BloodBank();
        updateBloodBankFromDTO(bloodBank, bloodBankDTO);
        BloodBank savedBloodBank = bloodBankRepository.save(bloodBank);
        return convertToDTO(savedBloodBank);
    }

    @Transactional
    public Optional<BloodBankDTO> updateBloodBank(Long id, BloodBankDTO bloodBankDTO) {
        return bloodBankRepository.findById(id)
                .map(bloodBank -> {
                    updateBloodBankFromDTO(bloodBank, bloodBankDTO);
                    return convertToDTO(bloodBankRepository.save(bloodBank));
                });
    }

    @Transactional
    public Optional<BloodBankDTO> toggleBloodBankStatus(Long id) {
        return bloodBankRepository.findById(id)
                .map(bloodBank -> {
                    bloodBank.setIsActive(!bloodBank.getIsActive());
                    return convertToDTO(bloodBankRepository.save(bloodBank));
                });
    }

    @Transactional
    public boolean deleteBloodBank(Long id) {
        return bloodBankRepository.findById(id)
                .map(bloodBank -> {
                    bloodBankRepository.delete(bloodBank);
                    return true;
                })
                .orElse(false);
    }

    private void updateBloodBankFromDTO(BloodBank bloodBank, BloodBankDTO dto) {
        bloodBank.setName(dto.getName());
        bloodBank.setAddress(dto.getAddress());
        bloodBank.setCity(dto.getCity());
        bloodBank.setState(dto.getState());
        bloodBank.setZipCode(dto.getZipCode());
        bloodBank.setCountry(dto.getCountry());
        bloodBank.setPhoneNumber(dto.getPhoneNumber());
        bloodBank.setEmail(dto.getEmail());
        bloodBank.setWebsite(dto.getWebsite());
        bloodBank.setLicenseNumber(dto.getLicenseNumber());
        bloodBank.setDescription(dto.getDescription());
        bloodBank.setIsActive(dto.getIsActive());
    }

    private BloodBankDTO convertToDTO(BloodBank bloodBank) {
        BloodBankDTO dto = new BloodBankDTO();
        dto.setId(bloodBank.getId());
        dto.setName(bloodBank.getName());
        dto.setAddress(bloodBank.getAddress());
        dto.setCity(bloodBank.getCity());
        dto.setState(bloodBank.getState());
        dto.setZipCode(bloodBank.getZipCode());
        dto.setCountry(bloodBank.getCountry());
        dto.setPhoneNumber(bloodBank.getPhoneNumber());
        dto.setEmail(bloodBank.getEmail());
        dto.setWebsite(bloodBank.getWebsite());
        dto.setLicenseNumber(bloodBank.getLicenseNumber());
        dto.setDescription(bloodBank.getDescription());
        dto.setIsActive(bloodBank.getIsActive());
        dto.setCreatedAt(bloodBank.getCreatedAt());
        dto.setUpdatedAt(bloodBank.getUpdatedAt());
        return dto;
    }
}