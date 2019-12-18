package com.mariamerkova.usermanagement.repository;

import com.mariamerkova.usermanagement.model.Role;

import java.util.List;

public interface RoleRepository {

    List<Role> findAll();

    void save(final Role role);

    Role findRoleByName(final String name);

    void update(final Role role);

    Role findById(final Long id);

    void delete(final Role role);
}


