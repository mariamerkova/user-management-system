package com.mariamerkova.usermanagement.service;

import com.mariamerkova.usermanagement.model.Privilege;
import com.mariamerkova.usermanagement.model.PrivilegeDTO;
import com.mariamerkova.usermanagement.repository.PrivilegeRepository;
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






    private PrivilegeDTO transformPrivilegeToPrivilegeDTO(final Privilege privilege) {
        PrivilegeDTO privilegeDTO = new PrivilegeDTO();
        privilegeDTO.setId(privilege.getId());
        privilegeDTO.setName(privilege.getName());
        return privilegeDTO;
    }

}
