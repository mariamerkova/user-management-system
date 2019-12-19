package com.mariamerkova.usermanagement.model;

import java.util.LinkedList;
import java.util.List;

public class RoleDTO {

    private Long id;
    private String name;
    private List<PrivilegeDTO> privilegeDTOS = new LinkedList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PrivilegeDTO> getPrivilegeDTOS() {
        return privilegeDTOS;
    }

    public void setPrivilegeDTOS(List<PrivilegeDTO> privilegeDTOS) {
        this.privilegeDTOS = privilegeDTOS;
    }
}
