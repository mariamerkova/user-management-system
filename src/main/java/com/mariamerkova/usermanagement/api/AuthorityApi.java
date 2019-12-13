package com.mariamerkova.usermanagement.api;

import com.mariamerkova.usermanagement.model.PrivilegeDTO;
import com.mariamerkova.usermanagement.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/authority")
public class AuthorityApi {

    @Autowired
    private AuthorityService authorityService;

    @GetMapping("/privilege")
    public ResponseEntity<List<PrivilegeDTO>> findAllPrivileges() {
        return ResponseEntity.ok(authorityService.findAll());
    }
}