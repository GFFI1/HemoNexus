package com.hemonexus.service;

import com.hemonexus.dto.*;
import com.hemonexus.model.*;
import com.hemonexus.repository.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final DonationRepository donationRepo;
    private final RequestRepository requestRepo;
    private final BloodBankRepository bankRepo;

    private final ModelMapper mapper = new ModelMapper();

    /* ---------- basic CRUD ------------------------------------ */

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepo.findAll().stream()
                .map(u -> mapper.map(u, UserDTO.class))
                .toList();
    }

    @Override
    public UserDTO createUser(UserDTO dto, Set<String> roles) {
        User u = mapper.map(dto, User.class);
        userRepo.save(u);
        return mapper.map(u, UserDTO.class);
    }

    @Override
    public Optional<UserDTO> getUserById(Long id) {
        return userRepo.findById(id).map(u -> mapper.map(u, UserDTO.class));
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO dto) {
        User u = userRepo.findById(id).orElseThrow();
        if (dto.getUsername() != null)
            u.setUsername(dto.getUsername());
        if (dto.getCity() != null)
            u.setCity(dto.getCity());
        if (dto.getFirstName() != null)
            u.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null)
            u.setLastName(dto.getLastName());
        userRepo.save(u);
        return mapper.map(u, UserDTO.class);
    }

    @Override
    public boolean deleteUser(Long id) {
        if (!userRepo.existsById(id))
            return false;
        userRepo.deleteById(id);
        return true;
    }

    /* ---------- admin donor list ------------------------------ */

    @Override
    public List<DonorDTO> listDonors() {

        return userRepo.findByRoles_Name(ERole.ROLE_DONOR).stream()
                .map(u -> {
                    // ensure we always have a DonorDTO instance
                    DonorDTO d = Optional.ofNullable(u.getDonor())
                            .map(dn -> mapper.map(dn, DonorDTO.class))
                            .orElse(new DonorDTO());

                    d.setId(u.getId());
                    d.setFirstName(Optional.ofNullable(u.getFirstName()).orElse("-"));
                    d.setLastName(Optional.ofNullable(u.getLastName()).orElse("-"));
                    d.setBloodType(Optional.ofNullable(d.getBloodType()).orElse("-"));
                    d.setCity(Optional.ofNullable(u.getCity()).orElse("-"));
                    d.setTotalDonations((int) donationRepo.countByDonor_User(u));
                    return d;
                })
                .toList();
    }

    /* ---------- admin requester list -------------------------- */

    @Override
    public List<RequesterDTO> listRequesters() {

        return userRepo.findByRoles_Name(ERole.ROLE_REQUESTER).stream()
                .map(u -> RequesterDTO.builder()
                        .id(u.getId())
                        .name( /* <- use .name NOT .username */
                                (Optional.ofNullable(u.getFirstName()).orElse("-") + " " +
                                        Optional.ofNullable(u.getLastName()).orElse("-")).trim())
                        .city(Optional.ofNullable(u.getCity()).orElse("-"))
                        .totalRequests((int) requestRepo.countByUser(u))
                        .build())
                .toList();
    }

    /* ---------- patch helpers --------------------------------- */

    @Override
    public DonorDTO patchDonor(Long id, Map<String, Object> p) {
        User u = userRepo.findById(id).orElseThrow();
        Donor d = Optional.ofNullable(u.getDonor()).orElse(new Donor());
        if (p.containsKey("firstName"))
            d.setFirstName((String) p.get("firstName"));
        if (p.containsKey("bloodType"))
            d.setBloodType((String) p.get("bloodType"));
        if (p.containsKey("city"))
            d.setCity((String) p.get("city"));
        u.setDonor(d);
        userRepo.save(u);
        return mapper.map(d, DonorDTO.class);
    }

    @Override
    public RequesterDTO patchRequester(Long id, Map<String, Object> p) {
        User u = userRepo.findById(id).orElseThrow();
        if (p.containsKey("city"))
            u.setCity((String) p.get("city"));
        userRepo.save(u);
        RequesterDTO r = new RequesterDTO();
        r.setId(u.getId());
        r.setUsername(u.getFirstName() + " " + u.getLastName());
        r.setCity(u.getCity());
        return r;
    }

    /* ---------- create blood bank ----------------------------- */

    @Override
    public BloodBankDTO createBank(BloodBankDTO dto) {
        User manager = userRepo.findById(dto.getManagerId())
                .orElseThrow(() -> new RuntimeException("Manager not found"));
        BloodBank bank = new BloodBank();
        bank.setName(dto.getName());
        bank.setCity(dto.getCity());
        bank.setManager(manager);
        bank = bankRepo.save(bank);

        BloodBankDTO out = new BloodBankDTO();
        out.setId(bank.getId());
        out.setName(bank.getName());
        out.setCity(bank.getCity());
        out.setManagerId(manager.getId());
        out.setManagerName(manager.getFirstName() + " " + manager.getLastName());
        return out;
    }
}
