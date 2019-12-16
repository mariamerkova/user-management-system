package com.mariamerkova.usermanagement.repository;

import com.mariamerkova.usermanagement.model.Privilege;

import java.util.List;

public interface PrivilegeRepository {

    List<Privilege> findAll();

    void save(final Privilege privilege);

    Privilege findPrivilegeByName(final String name);

    void update(final Privilege privilege);

    Privilege findById(final Long id);

    void deletePrivilege(final Privilege privilege);

}
