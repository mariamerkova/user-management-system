package com.mariamerkova.usermanagement.repository;

import com.mariamerkova.usermanagement.model.User;

import java.util.List;

public interface UserRepository {

    List<User> findAll();

    User findById(final Long id);

    void saveUser(final User user);

    void deleteUser(final User user);

    void updateUser(final User user);

    User findUserByUsername(final String username);


}
