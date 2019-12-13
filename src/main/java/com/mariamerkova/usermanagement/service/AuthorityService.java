package com.mariamerkova.usermanagement.service;

import com.mariamerkova.usermanagement.model.Privilege;
import com.mariamerkova.usermanagement.model.PrivilegeDTO;

import java.util.List;

public interface AuthorityService {

    List<PrivilegeDTO> findAll();

}
