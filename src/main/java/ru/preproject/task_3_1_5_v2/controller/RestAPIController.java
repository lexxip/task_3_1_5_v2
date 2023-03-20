package ru.preproject.task_3_1_5_v2.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.preproject.task_3_1_5_v2.dto.UserDTO;
import ru.preproject.task_3_1_5_v2.model.Role;
import ru.preproject.task_3_1_5_v2.service.RoleService;
import ru.preproject.task_3_1_5_v2.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@PreAuthorize("hasRole('ADMIN')")
public class RestAPIController {
    private final UserService userService;
    private final RoleService roleService;

    public RestAPIController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostMapping("/users")
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO userDTO) {
        UserDTO userDTOLocal = userService.create(userDTO);
        if (userDTOLocal == null) {
            return new ResponseEntity<>(null, HttpStatus.ALREADY_REPORTED);
        }
        return new ResponseEntity<>(userDTOLocal, HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> findAll() {
        List<UserDTO> userList = userService.findAll();
        if (userList.isEmpty() || userList == null) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @PutMapping("/users")
    public ResponseEntity<UserDTO> update(@RequestBody UserDTO userDTO) {
        UserDTO userDTOLocal = userService.update(userDTO);

        if (userDTOLocal == null) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(userDTOLocal, HttpStatus.OK);
    }

    @PatchMapping("/users")
    public ResponseEntity<UserDTO> updatePatch(@RequestBody UserDTO userDTO) {
        UserDTO userDTOLocal = userService.update(userDTO);

        if (userDTOLocal == null) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(userDTOLocal, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (userService.findOne(id) == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        userService.delete(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> findOne(@PathVariable Long id) {
        UserDTO userDTO = userService.findOne(id);
        if (userDTO == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping("/roles/{id}")
    public ResponseEntity<Role> findOneRole(@PathVariable Long id) {
        Role role = roleService.findOne(id);
        if (role == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(role, HttpStatus.OK);
    }
}
