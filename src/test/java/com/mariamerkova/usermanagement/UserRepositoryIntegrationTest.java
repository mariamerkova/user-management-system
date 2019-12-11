package com.mariamerkova.usermanagement;

import com.mariamerkova.usermanagement.model.CredentialsDTO;
import com.mariamerkova.usermanagement.model.User;
import com.mariamerkova.usermanagement.model.UserDTO;
import com.mariamerkova.usermanagement.repository.UserRepository;
import com.mariamerkova.usermanagement.service.UserService;
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

	@Autowired
	private UserService userService;

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

	@Test
	@DisplayName("Test updation")
	@Transactional
	@Rollback
	void testUpdateOfUser() {
		CredentialsDTO credentialsDTO= new CredentialsDTO();
		credentialsDTO.setUsername("bebe");
		credentialsDTO.setPassword("mimiTo7");

		UserDTO persistedUser = userService.save(credentialsDTO); // <- Тук този метод ти връща UserDTO като в него има всички полета които се създават по подразбиране + ид-то което се създава автоматично

		UserDTO userDTO = new UserDTO(); // Създаваш НОВА инстанция на този обект, като в него всички полета са нул, освен тези които по-доло си попълнила
		userDTO.setFirstName("Mi6i");
		userDTO.setLastName("Merkov");
		userDTO.setBirthDate(LocalDate.of(1991, Month.JULY.getValue(), 24));
		userDTO.setUsername("bebe");

		userService.update(userDTO);



		UserDTO persistedUserDTO = userService.findById(persistedUser.getId()); // И на тази стъпка се пробваш да заредиш от базата УсерДТО по ид, но ид-то тук ти е нул, защото горе не си го сетнала
		Assertions.assertThat(persistedUserDTO).isNotNull();


		Assertions.assertThat(persistedUserDTO.getFirstName()).isEqualTo("Mi6i");

		Assertions.assertThat(persistedUserDTO.getLastName()).isEqualTo("Merkov");
		Assertions.assertThat(persistedUserDTO.getBirthDate()).isEqualTo(LocalDate.of(1991, Month.JULY.getValue(), 24));
		Period expectedPeriod = Period.between(LocalDate.of(1991, Month.JULY.getValue(), 24), LocalDate.now());
		Assertions.assertThat(persistedUserDTO.getAge()).isEqualTo(expectedPeriod.getYears());
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
