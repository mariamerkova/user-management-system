package com.mariamerkova.usermanagement;

import com.mariamerkova.usermanagement.exception.PrivilegeAlreadyExistException;
import com.mariamerkova.usermanagement.exception.PrivilegeNotFoundExcepion;
import com.mariamerkova.usermanagement.exception.RequiredArgumentException;
import com.mariamerkova.usermanagement.model.Privilege;
import com.mariamerkova.usermanagement.model.PrivilegeDTO;
import com.mariamerkova.usermanagement.repository.PrivilegeRepository;
import com.mariamerkova.usermanagement.service.AuthorityService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

@SpringBootTest
public class PrivilegeRepositoryIntegrationTest {

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private AuthorityService authorityService;

    @Test
    @DisplayName("Test find all privileges")
    @Transactional
    @Rollback
    public void testFindAllPrivileges() {
        Assertions.assertThat(privilegeRepository.findAll().size()).isEqualTo(authorityService.findAll().size());
    }

    @Test
    @DisplayName("Test creation of a new privilege")
    @Transactional
    @Rollback
    void testCreationOfPrivilegeCase1() {
        Privilege privilege = new Privilege();
        privilege.setName("mimi");

        privilegeRepository.save(privilege);
        Assertions.assertThat(privilege).isNotNull();
    }

    @Test
    @DisplayName("Test creation of a new privilege , throwing PrivilegeAlreadyExistException ")
    @Transactional
    @Rollback
    void testCreationOfPrivilegeCase2() {
        Privilege privilege = new Privilege();
        privilege.setName("mimi");
        privilegeRepository.save(privilege);
        PrivilegeDTO privilegeDTO = new PrivilegeDTO();
        privilegeDTO.setName("mimi");

        Assertions.assertThatThrownBy(() -> authorityService.save(privilegeDTO)).isInstanceOf(PrivilegeAlreadyExistException.class);
    }

    @Test
    @DisplayName("Test modification of a new privilege")
    @Transactional
    @Rollback
    void testModificationOfPrivilegerCase1() {
        Privilege privilege = new Privilege();
        privilege.setName("mimi");

        privilegeRepository.save(privilege);
        Privilege persistedPrivilege = privilegeRepository.findById(privilege.getId());
        persistedPrivilege.setName("misho");
        privilegeRepository.update(privilege);

        persistedPrivilege = privilegeRepository.findById(privilege.getId());

        Assertions.assertThat(persistedPrivilege.getName()).isEqualTo("misho");
    }


    @Test
    @DisplayName("Test modification of a new privilege, throwing  RequiredArgumentException with name")
    @Transactional
    @Rollback
    void testModificationOfPrivilegerCase2() {
        PrivilegeDTO privilegeDTO = new PrivilegeDTO();
        privilegeDTO.setId(1L);

        Assertions.assertThatThrownBy(() -> authorityService.update(privilegeDTO)).isInstanceOf(RequiredArgumentException.class);
    }

    @Test
    @DisplayName("Test modification of a new privilege, throwing  RequiredArgumentException with id")
    @Transactional
    @Rollback
    void testModificationOfPrivilegerCase3() {
        PrivilegeDTO privilegeDTO = new PrivilegeDTO();
        privilegeDTO.setName("mimi");

        Assertions.assertThatThrownBy(() -> authorityService.update(privilegeDTO)).isInstanceOf(RequiredArgumentException.class);
    }

    @Test
    @DisplayName("Test modification of a new privilege, throwing PrivilegeNotFoundExcepion")
    @Transactional
    @Rollback
    void testModificationOfPrivilegerCase4() {
        Privilege privilege = new Privilege();
        privilege.setId(1l);
        privilege.setName("mimi");

        PrivilegeDTO privilegeDTO = new PrivilegeDTO();
        privilegeDTO.setId(privilege.getId());
        privilegeDTO.setName(privilege.getName());

        Assertions.assertThatThrownBy(() -> authorityService.update(privilegeDTO)).isInstanceOf(PrivilegeNotFoundExcepion.class);
    }


    @Test
    @DisplayName("Test modification of a new privilege, throwing PrivilegeAlreadyExistException ")
    @Transactional
    @Rollback
    void testModificationOfPrivilegerCase5() {
        PrivilegeDTO privilegeDTO = new PrivilegeDTO();
        privilegeDTO.setName("mimi");

        PrivilegeDTO secondPrivilegeDTO = new PrivilegeDTO();
        secondPrivilegeDTO.setName("ivan");

        PrivilegeDTO firstPersistedEntity = authorityService.save(privilegeDTO);
        PrivilegeDTO secondPersistedEntity = authorityService.save(secondPrivilegeDTO);

        secondPersistedEntity.setName("mimi");

        Assertions.assertThatThrownBy(() -> authorityService.update(secondPersistedEntity)).isInstanceOf(PrivilegeAlreadyExistException.class);
    }

    @Test
    @DisplayName("Test deletion of privilege")
    @Transactional
    @Rollback
    void testDeletionOfPrivilegeCase1() {
        Privilege privilege = new Privilege();
        privilege.setName("mimi");
        privilegeRepository.save(privilege);
        Privilege persistedPrivilege = privilegeRepository.findById(privilege.getId());
        Assertions.assertThat(persistedPrivilege).isNotNull();

        privilegeRepository.deletePrivilege(privilege);
        persistedPrivilege = privilegeRepository.findById(privilege.getId());
        Assertions.assertThat(persistedPrivilege).isNull();

    }

    @Test
    @DisplayName("Test deletion of privilege, throwing PrivilegeNotFoundException")
    @Transactional
    @Rollback
    void testDeletionOfPrivilegeCase2() {
        Privilege privilege = new Privilege();
        Assertions.assertThatThrownBy(()->  authorityService.delete(privilege.getId())).isInstanceOf(PrivilegeNotFoundExcepion.class);
    }


}
