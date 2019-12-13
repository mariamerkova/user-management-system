package com.mariamerkova.usermanagement;

import com.mariamerkova.usermanagement.exception.PrivilegeAlreadyExistException;
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
}
