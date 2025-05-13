package com.hemonexus.service;

import com.hemonexus.dto.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface UserService {

    /* --- CRUD already in your original code ------------------- */
    UserDTO createUser(UserDTO dto, Set<String> roles);

    Optional<UserDTO> getUserById(Long id);

    UserDTO updateUser(Long id, UserDTO dto);

    boolean deleteUser(Long id);

    /* NEW – for UserController list ---------------------------- */
    List<UserDTO> getAllUsers();

    /* --- Admin extras ----------------------------------------- */
    List<DonorDTO> listDonors();

    List<RequesterDTO> listRequesters();

    DonorDTO patchDonor(Long id, Map<String, Object> patch);

    RequesterDTO patchRequester(Long id, Map<String, Object> patch);

    BloodBankDTO createBank(BloodBankDTO dto);
}
