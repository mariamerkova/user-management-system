package com.mariamerkova.usermanagement.service;

import com.mariamerkova.usermanagement.exception.*;
import com.mariamerkova.usermanagement.model.Privilege;
import com.mariamerkova.usermanagement.model.PrivilegeDTO;
import com.mariamerkova.usermanagement.model.Role;
import com.mariamerkova.usermanagement.model.RoleDTO;
import com.mariamerkova.usermanagement.repository.PrivilegeRepository;
import com.mariamerkova.usermanagement.repository.RoleRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;

@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Override
    public List<PrivilegeDTO> findAll() {
        List<PrivilegeDTO> privilegeDTOList = new LinkedList<>();
        List<Privilege> privileges = privilegeRepository.findAll();

        for (Privilege privilege : privileges) {
            privilegeDTOList.add(transformPrivilegeToPrivilegeDTO(privilege));
        }

        return privilegeDTOList;
    }

    @Override
    @Transactional
    public PrivilegeDTO savePrivilege(final PrivilegeDTO privilegeDTO) {
        Privilege privilege = new Privilege();
        privilege.setName(privilegeDTO.getName());
        privilege.setId(privilegeDTO.getId());

        if (StringUtils.isBlank(privilege.getName())) {
            throw new RequiredArgumentException();
        }

        if (privilegeRepository.findPrivilegeByName(privilege.getName()) != null) {
            throw new PrivilegeAlreadyExistException();
        }

        privilegeRepository.save(privilege);

        return transformPrivilegeToPrivilegeDTO(privilege);
    }

    @Override
    @Transactional
    public PrivilegeDTO update(final PrivilegeDTO privilegeDTO) {
        if (StringUtils.isBlank(privilegeDTO.getName())) {
            throw new RequiredArgumentException();
        }

        if (privilegeDTO.getId() == null) {
            throw new RequiredArgumentException();
        }

        Privilege persistedPrivilege = privilegeRepository.findById(privilegeDTO.getId());

        if (persistedPrivilege == null) {
            throw new PrivilegeNotFoundExcepion();
        }

        Privilege  persistedPrivilegeWithSameName = privilegeRepository.findPrivilegeByName(privilegeDTO.getName());

        if (persistedPrivilegeWithSameName != null && persistedPrivilegeWithSameName.getId().compareTo(privilegeDTO.getId()) != 0) {
            throw new PrivilegeAlreadyExistException();
        }

        persistedPrivilege.setName(privilegeDTO.getName());

        privilegeRepository.update(persistedPrivilege);
        return transformPrivilegeToPrivilegeDTO(persistedPrivilege);
    }

    @Override
    @Transactional
    public boolean delete(final Long id) {
        Privilege privilege = privilegeRepository.findById(id);
        if (privilege == null) {
            throw new PrivilegeNotFoundExcepion();
        }

        privilegeRepository.deletePrivilege(privilege);
        return true;
    }

    private PrivilegeDTO transformPrivilegeToPrivilegeDTO(final Privilege privilege) {
        PrivilegeDTO privilegeDTO = new PrivilegeDTO();
        privilegeDTO.setId(privilege.getId());
        privilegeDTO.setName(privilege.getName());
        return privilegeDTO;
    }

    @Override
    public List<RoleDTO> findAllRoles() {
        List<RoleDTO> roleDTOList = new LinkedList<>();
        List<Role> roles = roleRepository.findAll();

        for (Role role : roles) {
            roleDTOList.add(transformRoleToRoleDTO(role));
        }
        return roleDTOList;
    }

    @Override
    @Transactional
    public RoleDTO saveRole(final RoleDTO roleDTO) {
        Role role = new Role();
        role.setId(roleDTO.getId());
        role.setName(roleDTO.getName());

        if (StringUtils.isBlank(role.getName())) {
            throw new RequiredArgumentException();
        }

        if (roleRepository.findRoleByName(role.getName()) != null) {
            throw new RoleAlreadyExistException();
        }

        roleRepository.save(role);

        if (!roleDTO.getPrivilegeDTOS().isEmpty()) {
            for (PrivilegeDTO privilegeDTO : roleDTO.getPrivilegeDTOS()) {
                Privilege privilege = privilegeRepository.findById(privilegeDTO.getId());
                if (privilege != null) {
                    role.getPrivileges().add(privilege);
                    privilege.getRoles().add(role);
                    roleRepository.update(role);
                    privilegeRepository.update(privilege);
                }
            }
        }

        return transformRoleToRoleDTO(role);
    }

    @Transactional
    @Override
    public RoleDTO updateRole(final RoleDTO roleDTO) {
        if (StringUtils.isBlank(roleDTO.getName())) {
            throw new RequiredArgumentException();
        }

        if (roleDTO.getId() == null) {
            throw new RequiredArgumentException();
        }

        Role persistedRole = roleRepository.findById(roleDTO.getId());

        if (persistedRole == null) {
            throw new RoleNotFoundException();
        }

        Role persistedRoleWithSameName = roleRepository.findRoleByName(roleDTO.getName());

        if (persistedRoleWithSameName != null && persistedRoleWithSameName.getId().compareTo(roleDTO.getId()) !=0) {
            throw new RoleAlreadyExistException();
        }

        persistedRole.setName(roleDTO.getName());

        Role role = roleRepository.findById(roleDTO.getId());
        List<Privilege> newPrivileges = new LinkedList<>();
        List<Privilege> currentPrivileges = role.getPrivileges();

        for (Privilege currentPrivilege : currentPrivileges) {
            if (roleDTO.getPrivilegeDTOS().stream().anyMatch(privilegeDTO -> currentPrivilege.getId().compareTo(privilegeDTO.getId()) == 0)) {
                newPrivileges.add(currentPrivilege);
            }
        }

        for (PrivilegeDTO privilegeDTO : roleDTO.getPrivilegeDTOS()) {
            if (currentPrivileges.stream().noneMatch(currentPrivilege -> currentPrivilege.getId().compareTo(privilegeDTO.getId())== 0)) {
                Privilege privilege = privilegeRepository.findById(privilegeDTO.getId());
                privilege.getRoles().add(role);
                privilegeRepository.update(privilege);
                newPrivileges.add(privilege);
            }
        }

        for (Privilege currentPrivilege : currentPrivileges) {
            if (newPrivileges.stream().noneMatch(newPrivilege -> currentPrivilege.getId().compareTo(newPrivilege.getId()) == 0)) {
                currentPrivilege.getRoles().remove(role);
                privilegeRepository.update(currentPrivilege);
            }
        }

        role.setPrivileges(newPrivileges);
        roleRepository.update(persistedRole);
        return transformRoleToRoleDTO(persistedRole);
    }

    @Transactional
    @Override
    public boolean deleteRole(final Long id) {
        Role role = roleRepository.findById(id);
        if (role == null) {
            throw new RoleNotFoundException();
        }

        roleRepository.delete(role);
        return true;
    }

    private RoleDTO transformRoleToRoleDTO(final Role role) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(role.getId());
        roleDTO.setName(role.getName());

        List<PrivilegeDTO> privilegeDTOS = new LinkedList<>();
        for(Privilege privilege : role.getPrivileges()) {
            PrivilegeDTO privilegeDTO = new PrivilegeDTO();
            privilegeDTO.setName(privilege.getName());
            privilegeDTO.setId(privilege.getId());
            privilegeDTOS.add(privilegeDTO);
        }

        roleDTO.setPrivilegeDTOS(privilegeDTOS);
        return roleDTO;
    }








}
