package com.mariamerkova.usermanagement.service;

import com.mariamerkova.usermanagement.exception.AuthenticationException;
import com.mariamerkova.usermanagement.exception.RequiredArgumentException;
import com.mariamerkova.usermanagement.exception.UserNotFoundException;
import com.mariamerkova.usermanagement.model.CredentialsDTO;
import com.mariamerkova.usermanagement.model.User;
import com.mariamerkova.usermanagement.model.UserDTO;
import com.mariamerkova.usermanagement.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;



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
            throw new RequiredArgumentException();
        }

        User user = new User();
        user.setPassword(credentials.getPassword());
        user.setUsername(credentials.getUsername());
        user.setUpdatedOn(LocalDateTime.now());
        user.setCreatedOn(LocalDateTime.now());
        userRepository.saveUser(user);



        return transformUserToUserDTO(user);
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
