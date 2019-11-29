package com.mariamerkova.usermanagement.service;

import com.mariamerkova.usermanagement.exception.UserNotFoundException;
import com.mariamerkova.usermanagement.model.User;
import com.mariamerkova.usermanagement.model.UserDTO;
import com.mariamerkova.usermanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    private UserDTO transformUserToUserDTO(final User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setAge(user.getAge());
        userDTO.setBirthDate(user.getBirthDate());
        return userDTO;
    }


}
