package com.example.BloodBankManagementSystem.services.impl;

import com.example.BloodBankManagementSystem.entities.Role;
import com.example.BloodBankManagementSystem.exceptions.NotFoundException;
import com.example.BloodBankManagementSystem.payload.RoleDto;
import com.example.BloodBankManagementSystem.repositories.RoleRepository;
import com.example.BloodBankManagementSystem.services.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ModelMapper modelMapper;

    public Role getRole(String roleName){
        if(roleRepository.findById(roleName).isPresent()){
            return roleRepository.findById(roleName).get();
        }
        throw new NotFoundException("Role with the RoleName "+roleName+" is Not Found");
    }

    @Override
    public RoleDto getRoleById(String roleName) {
        Role role=getRole(roleName);
        return RoleToDto(role);
    }

    @Override
    public List<RoleDto> getAllRoles() {
        List<Role> roleList=roleRepository.findAll();
        List<RoleDto> roleDtoList=new ArrayList<>();
        roleList.stream().map((role)->roleDtoList.add(RoleToDto(role))
        ).collect(Collectors.toList());
        return roleDtoList;
    }

    @Override
    public void deleteRole(String roleName) {
        Role role=getRole(roleName);
        roleRepository.delete(role);
    }

    @Override
    public RoleDto updateRole(RoleDto roleDto, String roleName) {
        Role role=getRole(roleName);
        role.setRoleDiscription(roleDto.getRoleDiscription());
        Role updatedRole=roleRepository.save(role);
        return RoleToDto(updatedRole);
    }

    @Override
    public RoleDto createRole(RoleDto roleDto) {
        Role role=DtotoRole(roleDto);
        Role savedRole=roleRepository.save(role);
        return RoleToDto(savedRole);
    }

    public RoleDto RoleToDto(Role role){
        return modelMapper.map(role,RoleDto.class);
    }

    public Role DtotoRole(RoleDto roleDto){
        return modelMapper.map(roleDto,Role.class);
    }
}
