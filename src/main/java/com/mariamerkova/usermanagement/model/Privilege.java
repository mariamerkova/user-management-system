package com.mariamerkova.usermanagement.model;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "PRIVILEGE")
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PRIVILEGE")
    private Long id;

    @Column(name = "NAME",  nullable = false)
    private String name;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "PRIVILEGE_ROLE",
            joinColumns = { @JoinColumn(name = "privilegeid_privilege")},
            inverseJoinColumns = { @JoinColumn(name = "roleid_role")}
    )
    List<Role> roles = new LinkedList<>();

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

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}




