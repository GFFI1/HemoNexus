package com.hemonexus.service;

import com.hemonexus.dto.*;
import com.hemonexus.model.*;
import com.hemonexus.repository.RoleRepository;
import com.hemonexus.repository.UserRepository;
import org.apache.commons.beanutils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.beanutils.BeanUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repo;
    private final RoleRepository roleRepo;
    private final ModelMapper mapper = new ModelMapper();

    /* ---------------- CRUD used by existing UserController ---------------- */

    @Override
    public List<UserDTO> getAllUsers() {
        return repo.findAll().stream().map(u -> mapper.map(u, UserDTO.class)).toList();
    }

    @Override
    public Optional<UserDTO> getUserById(Long id) {
        return repo.findById(id).map(u -> mapper.map(u, UserDTO.class));
    }

    @Override
    public UserDTO createUser(UserDTO dto, Set<String> roles) {
        User u = mapper.map(dto, User.class);
        Set<Role> r = new HashSet<>();
        for (String key : roles) {
            ERole e = ERole.valueOf("ROLE_" + key.toUpperCase());
            r.add(roleRepo.findByName(e).orElseThrow());
        }
        u.setRoles(r);
        return mapper.map(repo.save(u), UserDTO.class);
    }

    @Override
    public Optional<UserDTO> updateUser(Long id, UserDTO dto) {
        return repo.findById(id).map(u -> {
            u.setEmail(dto.getEmail());
            u.setPhoneNumber(dto.getPhoneNumber());
            return mapper.map(repo.save(u), UserDTO.class);
        });
    }

    @Override
    public boolean deleteUser(Long id) {
        if (!repo.existsById(id))
            return false;
        repo.deleteById(id);
        return true;
    }

    /* -------------------- Admin extras -------------------- */

    @Override
    public List<DonorDTO> listDonors() {
        return repo.findByRoles_Name("ROLE_DONOR")
                .stream().map(u -> mapper.map(u, DonorDTO.class)).toList();
    }

    @Override
    public List<RequesterDTO> listRequesters() {
        return repo.findByRoles_Name("ROLE_REQUESTER")
                .stream().map(u -> mapper.map(u, RequesterDTO.class)).toList();
    }

    @Override
    @Transactional
    public DonorDTO patchDonor(Long id, Map<String, Object> patch) {
        User u = repo.findById(id).orElseThrow();
        patch.forEach((k, v) -> {
            try {
                BeanUtils.setProperty(u, k, v);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });
        return mapper.map(u, DonorDTO.class);
    }

    @Override
    @Transactional
    public RequesterDTO patchRequester(Long id, Map<String, Object> patch) {
        User u = repo.findById(id).orElseThrow();
        patch.forEach((k, v) -> {
            try {
                BeanUtils.setProperty(u, k, v);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });
        return mapper.map(u, RequesterDTO.class);
    }
}
