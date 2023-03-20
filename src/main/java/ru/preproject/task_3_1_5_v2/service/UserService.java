package ru.preproject.task_3_1_5_v2.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.preproject.task_3_1_5_v2.dto.UserDTO;
import ru.preproject.task_3_1_5_v2.model.Role;
import ru.preproject.task_3_1_5_v2.model.User;
import ru.preproject.task_3_1_5_v2.repository.UserRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, RoleService roleService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Transactional(readOnly = true)
    public UserDTO findOne(Long id) {
        User user = userRepository.findById(id).orElse(null);
        return (user == null ? null : convertEntityToDTO(user));
    }

    @Transactional
    public UserDTO create(UserDTO userDTO) {
        if (userDTO.getPassword() == null || userDTO.getPassword().isBlank() || userRepository.findUserByEmail(userDTO.getEmail()) != null) {
            return null;
        }

        userDTO.setId(null);
        User user = userRepository.save(User.builder()
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .age(userDTO.getAge())
                .email(userDTO.getEmail())
                .password(bCryptPasswordEncoder.encode(userDTO.getPassword()))
                .roles(convertToSystemRoles(userDTO.getRoles()))
                .build());
        return convertEntityToDTO(user);
    }

    @Transactional(readOnly = true)
    public List<UserDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserDTO update(UserDTO userDTO) {
        User user = userRepository.findById(userDTO.getId()).orElse(null);

        if (user == null || !user.getEmail().equals(userDTO.getEmail())) {
            return null;
        }

        userRepository.save(convertDTOToEntity(userDTO, user));
        userDTO.setPassword("");
        return userDTO;
    }

    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    private UserDTO convertEntityToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .age(user.getAge())
                .email(user.getEmail())
                .password(user.getPassword())
                .roles(convertToDtoRoles(user.getRoles()))
                .build();
    }

    private User convertDTOToEntity(UserDTO userDTO, User user) {
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setAge(userDTO.getAge());
        user.setRoles(convertToSystemRoles(userDTO.getRoles()));

        String password = userDTO.getPassword();
        if (!password.isBlank()) {
            user.setPassword(bCryptPasswordEncoder.encode(password));
        }

        return user;
    }

    private Set<Long> convertToDtoRoles(Set<Role> roles) {
        return roles.stream().map(role -> role.getId()).collect(Collectors.toSet());
    }

    private Set<Role> convertToSystemRoles(Set<Long> roles) {
        return roles.stream().map(aLong -> roleService.findOne(aLong)).collect(Collectors.toSet());
    }

}
