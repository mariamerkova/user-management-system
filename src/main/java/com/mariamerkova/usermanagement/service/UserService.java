package com.mariamerkova.usermanagement.service;

import com.mariamerkova.usermanagement.exception.UserNotFoundException;
import com.mariamerkova.usermanagement.model.UserDTO;

import java.util.List;

public interface UserService {

    List<UserDTO> findAll();

    UserDTO findById(final Long id) throws UserNotFoundException;
}
