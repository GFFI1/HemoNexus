package com.hemonexus.controller;

import com.hemonexus.dto.*;
import com.hemonexus.model.*;
import com.hemonexus.repository.RoleRepository;
import com.hemonexus.repository.UserRepository;
import com.hemonexus.security.jwt.JwtUtils;
import com.hemonexus.security.services.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private JwtUtils jwtUtils;

    /* ───────────────────────── SIGN-IN ───────────────────────── */
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginDTO dto) {

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt = jwtUtils.generateJwtToken(auth);

        UserDetailsImpl ud = (UserDetailsImpl) auth.getPrincipal();
        List<String> roles = ud.getAuthorities()
                .stream().map(a -> a.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponseDTO(jwt, ud.getId(),
                ud.getUsername(), ud.getEmail(), roles));
    }

    /* ───────────────────────── SIGN-UP ───────────────────────── */
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupDTO dto) {

        if (userRepository.existsByUsername(dto.getUsername()))
            return ResponseEntity.badRequest().body(
                    new MessageResponseDTO("Error: Username is already taken!"));

        if (userRepository.existsByEmail(dto.getEmail()))
            return ResponseEntity.badRequest().body(
                    new MessageResponseDTO("Error: Email is already in use!"));

        /* 1 create user */
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(encoder.encode(dto.getPassword()));
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhoneNumber(dto.getPhoneNumber());

        /* 2 resolve roles */
        Set<Role> roles = new HashSet<>();
        Set<String> requested = Optional.ofNullable(dto.getRoles())
                .orElse(Collections.emptySet());

        if (requested.isEmpty()) { // default to DONOR
            roles.add(fetchRole(ERole.ROLE_DONOR));
        } else {
            for (String key : requested) {
                switch (key.toLowerCase()) {
                    case "admin" -> roles.add(fetchRole(ERole.ROLE_ADMIN));
                    case "mod" -> roles.add(fetchRole(ERole.ROLE_MODERATOR));
                    case "blood_bank" -> roles.add(fetchRole(ERole.ROLE_BLOOD_BANK_ADMIN));
                    case "requester" -> roles.add(fetchRole(ERole.ROLE_REQUESTER));
                    case "donor" -> roles.add(fetchRole(ERole.ROLE_DONOR));
                    default -> roles.add(fetchRole(ERole.ROLE_DONOR));
                }
            }
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponseDTO("User registered successfully!"));
    }

    /* ───────────────────────── helper ───────────────────────── */
    private Role fetchRole(ERole eRole) {
        return roleRepository.findByName(eRole)
                .orElseThrow(() -> new RuntimeException("Error: role %s not found".formatted(eRole)));
    }
}
