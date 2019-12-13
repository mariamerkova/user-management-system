package com.mariamerkova.usermanagement.service;

import com.mariamerkova.usermanagement.exception.PrivilegeAlreadyExistException;
import com.mariamerkova.usermanagement.exception.RequiredArgumentException;
import com.mariamerkova.usermanagement.model.Privilege;
import com.mariamerkova.usermanagement.model.PrivilegeDTO;
import com.mariamerkova.usermanagement.repository.PrivilegeRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    private PrivilegeDTO transformPrivilegeToPrivilegeDTO(final Privilege privilege) {
        PrivilegeDTO privilegeDTO = new PrivilegeDTO();
        privilegeDTO.setId(privilege.getId());
        privilegeDTO.setName(privilege.getName());
        return privilegeDTO;
    }

}
