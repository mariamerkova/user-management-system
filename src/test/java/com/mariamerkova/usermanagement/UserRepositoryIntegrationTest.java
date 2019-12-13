package com.mariamerkova.usermanagement;

import com.mariamerkova.usermanagement.exception.AuthenticationException;
import com.mariamerkova.usermanagement.exception.RequiredArgumentException;
import com.mariamerkova.usermanagement.exception.RequiredMinSizePasswordException;
import com.mariamerkova.usermanagement.exception.UserNotFoundException;
import com.mariamerkova.usermanagement.model.CredentialsDTO;
import com.mariamerkova.usermanagement.model.User;
import com.mariamerkova.usermanagement.model.UserDTO;
import com.mariamerkova.usermanagement.repository.UserRepository;
import com.mariamerkova.usermanagement.service.UserService;
import org.apache.commons.lang3.math.NumberUtils;
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
	@DisplayName("Test fetching successfully user, when it exist in DB")
	@Transactional
	@Rollback
	void testFindByIdCase1() {
		User user = new User();
		user.setUsername("Maria");
		user.setLastName("Merkova");
		user.setUsername("mariamerkova");
		user.setPassword("somestrongpassword");
		user.setCreatedOn(LocalDateTime.now());
		user.setUpdatedOn(LocalDateTime.now());

		userRepository.saveUser(user);

		try {
			UserDTO persistedUser = userService.findById(user.getIdUser());
			Assertions.assertThat(persistedUser).isNotNull();
		} catch (Exception e) {
			Assertions.fail("Must no throw any exception here", e);
		}

	}

	@Test
	@DisplayName("Test fetching user, but throwing UserNotFoundException")
	@Transactional
	@Rollback
	void testFindByIdCase2() {
		Assertions.assertThatThrownBy(() -> userService.findById(Long.MAX_VALUE)).isInstanceOf(UserNotFoundException.class);
	}

	@Test
	@DisplayName("Test creation of a new user")
	@Transactional
	@Rollback
	void testCreationOfAUserCase1() {
		User user = createMockUser();
		userRepository.saveUser(user);
		User persistedUser = userRepository.findById(user.getIdUser());

		Assertions.assertThat(persistedUser).isNotNull();
		Assertions.assertThat(persistedUser.getFirstName()).isEqualTo(user.getFirstName());
	}

	@Test
	@DisplayName("Test creation of a new user, throwing RequiredMinSizePasswordException")
	@Transactional
	@Rollback
	void testCreationOfAUserCase2() {
		CredentialsDTO credentialsDTO = new CredentialsDTO();
		credentialsDTO.setUsername("mimi");
		credentialsDTO.setPassword("mimi");

		Assertions.assertThatThrownBy(() -> userService.save(credentialsDTO)).isInstanceOf(RequiredMinSizePasswordException.class);
	}

	@Test
	@DisplayName("Test creation of a new user, throwing AuthenticationException")
	@Transactional
	@Rollback
	void testCreationOfAUserCase3() {
		CredentialsDTO credentialsDTO = new CredentialsDTO();
		credentialsDTO.setUsername("mimi");
		credentialsDTO.setPassword("mimitoooo");

		userService.save(credentialsDTO); // тук си сигурна че първият път като подадеш такива креденшъли няма да гръмне
		Assertions.assertThatThrownBy(() -> userService.save(credentialsDTO)).isInstanceOf(AuthenticationException.class);
	}

	@Test
	@DisplayName("Test creation of a new user, throwing RequiredArgumentException with password equal to null")
	@Transactional
	@Rollback
	void testCreationOfAUserCase4() {
		CredentialsDTO credentialsDTO = new CredentialsDTO();
		credentialsDTO.setPassword("mmgmhggbgb");
		Assertions.assertThatThrownBy(() -> userService.save(credentialsDTO)).isInstanceOf(RequiredArgumentException.class);
	}

	@Test
	@DisplayName("Test creation of a new user, throwing RequiredArgumentException with username equal to null")
	@Transactional
	@Rollback
	void testCreationOfAUserCase5() {
		CredentialsDTO credentialsDTO = new CredentialsDTO();
		credentialsDTO.setUsername("mmgmhggbgb");
		Assertions.assertThatThrownBy(() -> userService.save(credentialsDTO)).isInstanceOf(RequiredArgumentException.class);
	}

	@Test
	@DisplayName("Test creation of a new user, throwing RequiredArgumentException with username and password equal to null")
	@Transactional
	@Rollback
	void testCreationOfAUserCase6() {
		CredentialsDTO credentialsDTO = new CredentialsDTO();
		Assertions.assertThatThrownBy(() -> userService.save(credentialsDTO)).isInstanceOf(RequiredArgumentException.class);
	}



	@Test
	@DisplayName("Test modification of a new user")
	@Transactional
	@Rollback
	void testModificationOfAUserCase1() {
		User user = createMockUser();
		userRepository.saveUser(user);
		User persistedUser = userRepository.findById(user.getIdUser());
		persistedUser.setFirstName("Mihail");
		userRepository.updateUser(user);
		persistedUser = userRepository.findById(user.getIdUser());

		Assertions.assertThat(persistedUser.getFirstName()).isEqualTo("Mihail");
	}

	@Test
	@DisplayName("Test modification of a new user, throwing RequiredArgumentException")
	@Transactional
	@Rollback
	void testModificationOfAUserCase2() {
		UserDTO userDTO = new UserDTO();
		userDTO.setBirthDate(LocalDate.now());
		userDTO.setFirstName("mimi");
		userDTO.setLastName("merkova");
		userDTO.setAge(29);
		Assertions.assertThatThrownBy(() -> userService.update(userDTO)).isInstanceOf(RequiredArgumentException.class);
	}

	@Test
	@DisplayName("Test modification of a new user, throwing UserNotFoundException")
	@Transactional
	@Rollback
	void testModificationOfAUserCase3() {
		CredentialsDTO credentialsDTO = new CredentialsDTO();
		credentialsDTO.setUsername("gfgfvff");
		credentialsDTO.setPassword("fdfdfdfsdf");

		UserDTO userDTO = new UserDTO();
		userDTO.setUsername(credentialsDTO.getUsername());


		Assertions.assertThatThrownBy(() -> userService.update(userDTO)).isInstanceOf(UserNotFoundException.class);
	}



	@Test
	@DisplayName("Test deletion of a new user")
	@Transactional
	@Rollback
	void testDeletionOfAUserCase1() {
		User user = createMockUser();
		userRepository.saveUser(user);
		User persistedUser = userRepository.findById(user.getIdUser());

		Assertions.assertThat(persistedUser).isNotNull();

		userRepository.deleteUser(persistedUser);
		persistedUser = userRepository.findById(user.getIdUser());
		Assertions.assertThat(persistedUser).isNull();
	}

	@Test
	@DisplayName("Test deletion of a new user, throwing UserNotFoundException")
	@Transactional
	@Rollback
	void testDeletionOfAUserCase2() {
		User user = new User();
		Assertions.assertThatThrownBy(()-> userService.delete(user.getIdUser())).isInstanceOf(UserNotFoundException.class);
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

	@Test
	@DisplayName("Test deletion")
	@Transactional
	@Rollback
	void testDeleteOperationOfUser() {
		CredentialsDTO credentialsDTO = new CredentialsDTO();
		credentialsDTO.setUsername("baby");
		credentialsDTO.setPassword("newBabyM");

		UserDTO persistedUser = userService.save(credentialsDTO);

		UserDTO persistedUserDTO = userService.findById(persistedUser.getId()); // Na tazi stupka zarejdash obekt ot bazata


		userService.delete(persistedUserDTO.getId()); // tuk podavash samo id kum delete i toi si vurshi negovata rabota
		// sled tazi stupka v bazata moje i da nqma nishto, no zaredeniqt obekt ot liniq 118 vse oshte ti si e zareden s nqkkavi danni, vkluchitelno i id
		//Assertions.assertThat(persistedUserDTO.getId()).isEqualTo(null); // tuk proverqvash dali id-to na zaredeniqt ti obekt ot predi malko ne e stanal null veqe. Ami nqma kak da stane, nikoj ne go iztril
		// pravilnoto tuk e da se probvash otnovo da zarediw obekt ot bazata s repozitory primerno i da vidiw dali shte ti vurne null
		try {
			UserDTO expectedDeletedUser = userService.findById(persistedUserDTO.getId());
			Assertions.fail("Must throw exception.");
		} catch (Exception e) {
			Assertions.assertThat(e).isInstanceOf(UserNotFoundException.class);
		}
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
