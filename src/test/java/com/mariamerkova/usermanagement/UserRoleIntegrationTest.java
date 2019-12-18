package com.mariamerkova.usermanagement;

import com.mariamerkova.usermanagement.model.Role;
import com.mariamerkova.usermanagement.model.User;
import com.mariamerkova.usermanagement.repository.RoleRepository;
import com.mariamerkova.usermanagement.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
public class UserRoleIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    @DisplayName("Test user-role table")
    @Transactional
    @Rollback
    void testUserRoleMapping() {
        User user = new User();
        user.setFirstName("an");
        user.setAge(27);
        user.setLastName("smith");
        user.setBirthDate(LocalDate.now());
        user.setCreatedOn(LocalDateTime.now());
        user.setUpdatedOn(LocalDateTime.now());
        user.setPassword("goodPassword");
        user.setUsername("an");
        userRepository.saveUser(user);
        Long userId = user.getIdUser();
        user = null;

        user = userRepository.findById(userId);
        Assertions.assertThat(user.getRoles().isEmpty());

        Role role = new Role();
        role.setName("boss");
        roleRepository.save(role);
        Long roleId = role.getId();
        role = null;

        role = roleRepository.findById(roleId);
        Assertions.assertThat(role.getUsers().isEmpty());

        user.getRoles().add(role);
        role.getUsers().add(user);

        userRepository.updateUser(user);
        roleRepository.update(role);
        user = null;
        role = null;

        user = userRepository.findById(userId);
        role = roleRepository.findById(roleId);

        Assertions.assertThat(user.getRoles().size()).isEqualTo(1);
        Assertions.assertThat(role.getUsers().size()).isEqualTo(1);

        Assertions.assertThat(user.getRoles().get(0).getId()).isEqualTo(role.getId());
        Assertions.assertThat(role.getUsers().get(0).getIdUser()).isEqualTo(user.getIdUser());




    }


}
