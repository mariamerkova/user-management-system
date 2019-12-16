package com.mariamerkova.usermanagement.service;

import com.mariamerkova.usermanagement.exception.PrivilegeAlreadyExistException;
import com.mariamerkova.usermanagement.exception.PrivilegeNotFoundExcepion;
import com.mariamerkova.usermanagement.exception.RequiredArgumentException;
import com.mariamerkova.usermanagement.model.Privilege;
import com.mariamerkova.usermanagement.model.PrivilegeDTO;
import com.mariamerkova.usermanagement.repository.PrivilegeRepository;
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
    public PrivilegeDTO save(final PrivilegeDTO privilegeDTO) {
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

        if (privilegeRepository.findById(privilegeDTO.getId()) == null) {
            throw new PrivilegeNotFoundExcepion();
        }

        Privilege  persistedPrivilegeWithSameName= privilegeRepository.findPrivilegeByName(privilegeDTO.getName());

        if (persistedPrivilegeWithSameName.getId() != null && persistedPrivilegeWithSameName.getId().compareTo(privilegeDTO.getId()) != 0) {
            throw new PrivilegeAlreadyExistException();
        }

        persistedPrivilegeWithSameName.setName(privilegeDTO.getName());

        privilegeRepository.update(persistedPrivilegeWithSameName);
        return transformPrivilegeToPrivilegeDTO(persistedPrivilegeWithSameName);
    }

    @Override
    @Transactional
    public boolean delete(final Long id) {
        Privilege privilege = privilegeRepository.findById(id);
        if (privilege == null) {
            throw new PrivilegeNotFoundExcepion();
        } else {
            privilegeRepository.deletePrivilege(privilege);
        }
        return true;
    }

    private PrivilegeDTO transformPrivilegeToPrivilegeDTO(final Privilege privilege) {
        PrivilegeDTO privilegeDTO = new PrivilegeDTO();
        privilegeDTO.setId(privilege.getId());
        privilegeDTO.setName(privilege.getName());
        return privilegeDTO;
    }

}
