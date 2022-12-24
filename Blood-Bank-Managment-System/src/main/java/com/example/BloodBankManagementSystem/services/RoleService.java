package com.example.BloodBankManagementSystem.services;

import com.example.BloodBankManagementSystem.payload.RoleDto;

import java.util.List;

public interface RoleService {

    public RoleDto getRoleById(String roleName);
    public List<RoleDto> getAllRoles();
    public void deleteRole(String roleName);
    public RoleDto updateRole(RoleDto roleDto,String roleName);
    public RoleDto createRole(RoleDto roleDto);
}
