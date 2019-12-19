package com.mariamerkova.usermanagement.service;

import com.mariamerkova.usermanagement.model.Privilege;
import com.mariamerkova.usermanagement.model.PrivilegeDTO;
import com.mariamerkova.usermanagement.model.RoleDTO;

import java.util.List;

public interface AuthorityService {

    List<PrivilegeDTO> findAll();

    PrivilegeDTO savePrivilege(final PrivilegeDTO privilegeDTO);

    PrivilegeDTO update(final PrivilegeDTO privilegeDTO);

    boolean delete(final Long id);

    List<RoleDTO> findAllRoles();

    RoleDTO saveRole(final RoleDTO roleDTO);

    RoleDTO updateRole(final RoleDTO roleDTO);

    boolean deleteRole(final Long id);

}
