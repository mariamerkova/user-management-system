package com.mariamerkova.usermanagement.api;

import com.mariamerkova.usermanagement.model.CredentialsDTO;
import com.mariamerkova.usermanagement.model.User;
import com.mariamerkova.usermanagement.model.UserDTO;
import com.mariamerkova.usermanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserApi {

    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<List<UserDTO>> listAllUsers() {
        return ResponseEntity.ok(userService.findAll());
   }

   @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findSpecificUser(@PathVariable final Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping
    public ResponseEntity<UserDTO>  save( @RequestBody final CredentialsDTO credentials) {
        return ResponseEntity.ok(userService.save(credentials));
    }

    @PutMapping
     public ResponseEntity<UserDTO> update(@RequestBody final UserDTO userDTO) {
        return ResponseEntity.ok(userService.update(userDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete( @PathVariable final Long id) {
        return ResponseEntity.ok(userService.delete(id));
    }
}
