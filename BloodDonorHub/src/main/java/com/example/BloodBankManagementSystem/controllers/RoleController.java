package com.example.BloodBankManagementSystem.controllers;

import com.example.BloodBankManagementSystem.payload.RoleDto;
import com.example.BloodBankManagementSystem.pojo.Response;
import com.example.BloodBankManagementSystem.pojo.Status;
import com.example.BloodBankManagementSystem.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/role")
public class RoleController {

    @Autowired
    RoleService roleService;

     @PreAuthorize("hasRole('ADMIN')")
     @PostMapping
     public Response<RoleDto> createRole(@Valid @RequestBody RoleDto roleDto){
         RoleDto createRoleDto=roleService.createRole(roleDto);
         Response<RoleDto> response=new Response<>();
         response.setBody(createRoleDto);
         response.setStatus(new Status(HttpStatus.OK.value(),true,"Role is created"));
         return response;
     }

     @PreAuthorize("hasRole('ADMIN')")
     @PutMapping(value="/{roleName}")
     public Response<RoleDto> updateRole(@RequestBody RoleDto roleDto,@PathVariable("roleName") String roleName){
         RoleDto updateRoleDto=roleService.updateRole(roleDto,roleName);
         Response<RoleDto> response=new Response<>();
         response.setBody(updateRoleDto);
         response.setStatus(new Status(HttpStatus.OK.value(),true,"Roleis updated"));
         return response;
     }

     @PreAuthorize("hasRole('ADMIN')")
     @GetMapping
     public Response<List<RoleDto>> getAllRoles(){
         List<RoleDto> roleDtoList=roleService.getAllRoles();
         Response<List<RoleDto>> response=new Response<>();
         response.setBody(roleDtoList);
         response.setStatus(new Status(HttpStatus.OK.value(), true,"Fetched all the roles"));
         return response;
     }

     @PreAuthorize("hasRole('ADMIN')")
     @GetMapping(value="/{roleName}")
     public Response<RoleDto> getRoleById(@PathVariable("roleName") String roleName){
         RoleDto roleDto=roleService.getRoleById(roleName);
         Response<RoleDto> response=new Response<>();
         response.setBody(roleDto);
         response.setStatus(new Status(HttpStatus.OK.value(),true,"Fetched the role by Id"));
         return response;
     }

     @PreAuthorize("hasRole('ADMIN')")
     @DeleteMapping(value = "/{roleName}")
     public Response<String> deleteRole(@PathVariable("roleName") String roleName){
         roleService.deleteRole(roleName);
         Response<String> response=new Response<>();
         response.setBody("Role is deleted successfully");
         response.setStatus(new Status(HttpStatus.OK.value(),true,"Delettion done"));
         return response;
     }

}
