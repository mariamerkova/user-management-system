package com.mariamerkova.usermanagement;

import com.mariamerkova.usermanagement.exception.RequiredArgumentException;
import com.mariamerkova.usermanagement.exception.RoleAlreadyExistException;
import com.mariamerkova.usermanagement.exception.RoleNotFoundException;
import com.mariamerkova.usermanagement.model.Role;
import com.mariamerkova.usermanagement.model.RoleDTO;
import com.mariamerkova.usermanagement.repository.RoleRepository;
import com.mariamerkova.usermanagement.service.AuthorityService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

@SpringBootTest
public class RoleRepositoryIntegrationTest {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthorityService authorityService;

    @Test
    @DisplayName("Test find all roles")
    @Transactional
    @Rollback
    public void testFindAllRoles() {
        Assertions.assertThat(roleRepository.findAll().size()).isEqualTo(authorityService.findAllRoles().size());
    }

    @Test
    @DisplayName("Test creation of role")
    @Transactional
    @Rollback
    public void testCreationOfRoleCase1() {
        Role role = new Role();
        role.setName("boss");
        roleRepository.save(role);

        Role perdistedRole = roleRepository.findById(role.getId());
        Assertions.assertThat(perdistedRole).isNotNull();

    }

    @Test
    @DisplayName("Test creation of role, throwing RoleAlreadyExistException")
    @Transactional
    @Rollback
    public void testCreationOfRoleCase2() {
        Role role = new Role();
        role.setName("boss");
        roleRepository.save(role);
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName("boss");

        Assertions.assertThatThrownBy(() -> authorityService.saveRole(roleDTO)).isInstanceOf(RoleAlreadyExistException.class);
    }


    @Test
    @DisplayName("Test modification of a new role")
    @Transactional
    @Rollback
    void testModificationOfRoleCase1() {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName("boss");

       RoleDTO persistedRoleDTO = authorityService.saveRole(roleDTO);
       persistedRoleDTO.setName("director");
       authorityService.updateRole(persistedRoleDTO);

       Role role = roleRepository.findById(persistedRoleDTO.getId());

        Assertions.assertThat(role.getName()).isEqualTo("director");
    }

    @Test
    @DisplayName("Test modification of a new role,throwing  RequiredArgumentException with name")
    @Transactional
    @Rollback
    void testModificationOfRoleCase2() {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(1L);

        Assertions.assertThatThrownBy(() -> authorityService.updateRole(roleDTO)).isInstanceOf(RequiredArgumentException.class);
    }

    @Test
    @DisplayName("Test modification of a new role,throwing  RequiredArgumentException with id")
    @Transactional
    @Rollback
    void testModificationOfRoleCase3() {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName("boss");

        Assertions.assertThatThrownBy(() -> authorityService.updateRole(roleDTO)).isInstanceOf(RequiredArgumentException.class);
    }


    @Test
    @DisplayName("Test modification of a new role,throwing  RoleNotFoundException")
    @Transactional
    @Rollback
    void testModificationOfRoleCase4() {
        Role role = new Role();
        role.setId(1L);
        role.setName("boss");

        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(role.getId());
        roleDTO.setName(role.getName());

        Assertions.assertThatThrownBy(() -> authorityService.updateRole(roleDTO)).isInstanceOf(RoleNotFoundException.class);
    }


    @Test
    @DisplayName("Test modification of a new role,throwing  RoleAlreadyExistException")
    @Transactional
    @Rollback
    void testModificationOfRoleCase5() {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName("boss");

        RoleDTO secondRoleDTO = new RoleDTO();
        secondRoleDTO.setName("director");

        RoleDTO firstPersistedEntity = authorityService.saveRole(roleDTO);
        RoleDTO secondPersistedEntity = authorityService.saveRole(secondRoleDTO);

        secondPersistedEntity.setName("boss");
        Assertions.assertThatThrownBy(() -> authorityService.updateRole(secondPersistedEntity)).isInstanceOf(RoleAlreadyExistException.class);
    }


    @Test
    @DisplayName("Test deletion of role")
    @Transactional
    @Rollback
    void testDeletionOfRoleCase1() {
        Role role = new Role();
        role.setName("boss");
        roleRepository.save(role);
        Role persistedRole = roleRepository.findById(role.getId());
        Assertions.assertThat(persistedRole).isNotNull();

        roleRepository.delete(role);
        persistedRole = roleRepository.findById(role.getId());
        Assertions.assertThat(persistedRole).isNull();
    }

    @Test
    @DisplayName("Test deletion of role, throwing RoleNotFoundException")
    @Transactional
    @Rollback
    void testDeletionOfRoleCase2() {
        Role role = new Role();
        Assertions.assertThatThrownBy(() -> authorityService.deleteRole(role.getId())).isInstanceOf(RoleNotFoundException.class);
    }
}
