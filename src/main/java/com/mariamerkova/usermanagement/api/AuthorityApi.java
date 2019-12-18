package com.mariamerkova.usermanagement.api;

import com.mariamerkova.usermanagement.model.PrivilegeDTO;
import com.mariamerkova.usermanagement.model.RoleDTO;
import com.mariamerkova.usermanagement.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/authority")
public class AuthorityApi {

    @Autowired
    private AuthorityService authorityService;

    @GetMapping("/privilege")
    public ResponseEntity<List<PrivilegeDTO>> findAllPrivileges() {
        return ResponseEntity.ok(authorityService.findAll());
    }

    @PostMapping("/privilege")
    public ResponseEntity<PrivilegeDTO> savePrivilege(PrivilegeDTO privilegeDTO) {
        return ResponseEntity.ok(authorityService.save(privilegeDTO));
    }

    @PutMapping("/privilege")
    public ResponseEntity<PrivilegeDTO> updatePrivilege(PrivilegeDTO privilegeDTO) {
        return ResponseEntity.ok(authorityService.update(privilegeDTO));
    }

    @DeleteMapping("/privilege/{id}")
    public ResponseEntity<Boolean> deletePrivilege(@PathVariable Long id) {
        return ResponseEntity.ok(authorityService.delete(id));
    }

    @GetMapping("/role")
    public ResponseEntity<List<RoleDTO>> findAllRoles() {
        return ResponseEntity.ok(authorityService.findAllRoles());
    }

    @PostMapping("/role")
    public  ResponseEntity<RoleDTO> saveRole(RoleDTO roleDTO) {
        return ResponseEntity.ok(authorityService.saveRole(roleDTO));
    }

    @PutMapping("/role")
    public ResponseEntity<RoleDTO> updateRole(RoleDTO roleDTO) {
        return ResponseEntity.ok(authorityService.updateRole(roleDTO));
    }

    @DeleteMapping("/role/{id}")
    public ResponseEntity<Boolean> deleteRole(@PathVariable Long id) {
        return ResponseEntity.ok(authorityService.deleteRole(id));
    }



}
