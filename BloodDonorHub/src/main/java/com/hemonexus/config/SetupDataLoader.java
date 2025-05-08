package com.hemonexus.config;

import com.hemonexus.model.ERole;
import com.hemonexus.model.Role;
import com.hemonexus.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SetupDataLoader implements ApplicationRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        createRoleIfNotFound(ERole.ROLE_USER);
        createRoleIfNotFound(ERole.ROLE_MODERATOR);
        createRoleIfNotFound(ERole.ROLE_ADMIN);
        createRoleIfNotFound(ERole.ROLE_BLOOD_BANK_ADMIN);
    }

    private void createRoleIfNotFound(ERole name) {
        if (!roleRepository.findByName(name).isPresent()) {
            Role role = new Role(name);
            roleRepository.save(role);
        }
    }
}