package com.mariamerkova.usermanagement.service;

import com.mariamerkova.usermanagement.exception.UserNotFoundException;
import com.mariamerkova.usermanagement.model.CredentialsDTO;
import com.mariamerkova.usermanagement.model.User;
import com.mariamerkova.usermanagement.model.UserDTO;

import java.util.List;

public interface UserService {

    List<UserDTO> findAll();

    UserDTO findById(final Long id) throws UserNotFoundException;

    UserDTO save(final CredentialsDTO credentials);

    UserDTO update(final UserDTO userDTO);
}
