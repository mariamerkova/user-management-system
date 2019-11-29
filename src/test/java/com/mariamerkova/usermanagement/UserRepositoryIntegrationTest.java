package com.mariamerkova.usermanagement;

import com.mariamerkova.usermanagement.model.User;
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
import java.time.Month;
import java.time.Period;

@SpringBootTest
class UserRepositoryIntegrationTest {

	@Autowired
	private UserRepository userRepository;

	@Test
	@DisplayName("Test creation of a new user")
	@Transactional
	@Rollback
	void testCreationOfAUser() {
		User user = createMockUser();
		userRepository.saveUser(user);
		User persistedUser = userRepository.findById(user.getIdUser());

		Assertions.assertThat(persistedUser).isNotNull();
		Assertions.assertThat(persistedUser.getFirstName()).isEqualTo(user.getFirstName());
	}

	@Test
	@DisplayName("Test modification of a new user")
	@Transactional
	@Rollback
	void testModificationOfAUser() {
		User user = createMockUser();
		userRepository.saveUser(user);
		User persistedUser = userRepository.findById(user.getIdUser());
		persistedUser.setFirstName("Mihail");
		userRepository.updateUser(user);
		persistedUser = userRepository.findById(user.getIdUser());

		Assertions.assertThat(persistedUser.getFirstName()).isEqualTo("Mihail");
	}

	@Test
	@DisplayName("Test deletion of a new user")
	@Transactional
	@Rollback
	void testDeletionOfAUser() {
		User user = createMockUser();
		userRepository.saveUser(user);
		User persistedUser = userRepository.findById(user.getIdUser());

		Assertions.assertThat(persistedUser).isNotNull();

		userRepository.deleteUser(persistedUser);
		persistedUser = userRepository.findById(user.getIdUser());
		Assertions.assertThat(persistedUser).isNull();
	}

	private User createMockUser() {
		User user = new User();
		user.setFirstName("Maria");
		user.setLastName("Merkova");
		user.setAge(Period.between(LocalDate.of(1990, Month.JULY, 24), LocalDate.now()).getYears());
		user.setUsername("mariamerkova");
		user.setPassword("mock-password123");
		user.setCreatedOn(LocalDateTime.now());
		user.setUpdatedOn(LocalDateTime.now());

		return user;
	}
}
