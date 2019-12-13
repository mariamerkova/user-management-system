package com.mariamerkova.usermanagement.repository;

import com.mariamerkova.usermanagement.model.Privilege;

import java.util.List;

public interface PrivilegeRepository {

    List<Privilege> findAll();

}
