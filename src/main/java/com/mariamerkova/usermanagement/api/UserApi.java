package com.mariamerkova.usermanagement.api;

import com.mariamerkova.usermanagement.model.UserDTO;
import com.mariamerkova.usermanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserApi {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> listAllUsers() {
        return ResponseEntity.ok(userService.findAll());
   }

   @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findSpecificUser(@PathVariable final Long userId) {
        return ResponseEntity.ok(userService.findById(userId));
    }
}
