package com.mariamerkova.usermanagement;

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
}
