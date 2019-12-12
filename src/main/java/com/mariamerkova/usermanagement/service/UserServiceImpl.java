package com.mariamerkova.usermanagement.service;

import com.mariamerkova.usermanagement.exception.AuthenticationException;
import com.mariamerkova.usermanagement.exception.RequiredArgumentException;
import com.mariamerkova.usermanagement.exception.RequiredMinSizePasswordException;
import com.mariamerkova.usermanagement.exception.UserNotFoundException;
import com.mariamerkova.usermanagement.model.CredentialsDTO;
import com.mariamerkova.usermanagement.model.User;
import com.mariamerkova.usermanagement.model.UserDTO;
import com.mariamerkova.usermanagement.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.LinkedList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);



    @Override
    public List<UserDTO> findAll() {
        List<UserDTO> userDTOList = new LinkedList<>();
        List<User> users = userRepository.findAll();

        for (User user : users) {
            userDTOList.add(transformUserToUserDTO(user));
        }

        return userDTOList;
    }

    @Override
    public UserDTO findById(final Long id) throws UserNotFoundException {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new UserNotFoundException(id);
        }
        return transformUserToUserDTO(user);
    }

    @Override
    @Transactional
    public UserDTO save(final CredentialsDTO credentials) {
        if (StringUtils.isBlank(credentials.getPassword())) {
            throw  new RequiredArgumentException();
        } else if (StringUtils.isBlank(credentials.getUsername())) {
            throw  new RequiredArgumentException();
        }

        if (userRepository.findUserByUsername(credentials.getUsername()) != null) {
            throw new AuthenticationException();
        }

        if (credentials.getPassword().length() < 6) {
            throw new RequiredMinSizePasswordException();
        }

        User user = new User();
        user.setPassword(credentials.getPassword());
        user.setUsername(credentials.getUsername());
        user.setUpdatedOn(LocalDateTime.now());
        user.setCreatedOn(LocalDateTime.now());
        userRepository.saveUser(user);



        return transformUserToUserDTO(user);
    }

    @Override
    @Transactional
    public UserDTO update(final UserDTO userDTO) {
        if (StringUtils.isBlank(userDTO.getUsername())) {
            throw  new RequiredArgumentException();
        }

        User user = userRepository.findUserByUsername(userDTO.getUsername());

        if (user == null) {
            throw new UserNotFoundException();
        }

        if (userDTO.getBirthDate() == null) {
            user.setAge(null);
        } else {
            LocalDate today = LocalDate.now();
            LocalDate birthday = userDTO.getBirthDate();

            Period p = Period.between(birthday, today);
            log.info("Years between this period are {}", p.getYears());
            user.setAge(p.getYears());
            user.setBirthDate(birthday);
        }

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());

        userRepository.updateUser(user);

        return transformUserToUserDTO(user);
    }

    @Override
    @Transactional
    public boolean delete(final Long id) {
        User user = userRepository.findById(id);
        if (user == null) {
            throw  new UserNotFoundException();
        } else {
            userRepository.deleteUser(user);
        }
        return true;
    }


    private User transformUserDTOToUser(final UserDTO userDTO) {
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setBirthDate(userDTO.getBirthDate());
        return user;
    }


    private UserDTO transformUserToUserDTO(final User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getIdUser());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setAge(user.getAge());
        userDTO.setBirthDate(user.getBirthDate());
        return userDTO;
    }




}
