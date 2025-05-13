package com.hemonexus.service;

import com.hemonexus.dto.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface UserService {

    UserDTO createUser(UserDTO dto, Set<String> roles);

    Optional<UserDTO> getUserById(Long id);

    UserDTO updateUser(Long id, UserDTO dto);

    boolean deleteUser(Long id);

    List<UserDTO> getAllUsers();

    List<DonorDTO> listDonors();

    List<RequesterDTO> listRequesters();

    DonorDTO patchDonor(Long id, Map<String, Object> patch);

    RequesterDTO patchRequester(Long id, Map<String, Object> patch);

    BloodBankDTO createBank(BloodBankDTO dto);
}
