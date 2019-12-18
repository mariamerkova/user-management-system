package com.mariamerkova.usermanagement;

import com.mariamerkova.usermanagement.model.Privilege;
import com.mariamerkova.usermanagement.model.Role;
import com.mariamerkova.usermanagement.repository.PrivilegeRepository;
import com.mariamerkova.usermanagement.repository.RoleRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

@SpringBootTest
public class PrivilegeRoleIntegrationTest {

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    @DisplayName("Test privilege-role table")
    @Transactional
    @Rollback
    void testPrivilegeRoleMapping() {
       Role role = new Role();
       role.setName("ROLE_USER");
       roleRepository.save(role);
       Long roleId = role.getId();
       role = null;

       role = roleRepository.findById(roleId);
       Assertions.assertThat(role.getPrivileges()).isEmpty();

       Privilege privilege = new Privilege();
       privilege.setName("access-admin-panel");
       privilegeRepository.save(privilege);
       Long privilegeId = privilege.getId();
       privilege = null;
       privilege = privilegeRepository.findById(privilegeId);
       Assertions.assertThat(privilege.getRoles()).isEmpty();
       role.getPrivileges().add(privilege);
       privilege.getRoles().add(role);

       roleRepository.update(role);
       privilegeRepository.update(privilege);
       role = null;
       privilege = null;

       role = roleRepository.findById(roleId);
       privilege = privilegeRepository.findById(privilegeId);

       Assertions.assertThat(role.getPrivileges().size()).isEqualTo(1);
       Assertions.assertThat(privilege.getRoles().size()).isEqualTo(1);

       Assertions.assertThat(role.getPrivileges().get(0).getId()).isEqualTo(privilege.getId());
       Assertions.assertThat(privilege.getRoles().get(0).getId()).isEqualTo(role.getId());
    }
}
