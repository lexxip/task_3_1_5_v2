package ru.preproject.task_3_1_5_v2.service;

import org.springframework.stereotype.Service;
import ru.preproject.task_3_1_5_v2.model.Role;
import ru.preproject.task_3_1_5_v2.repository.RoleRepository;

import java.util.List;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public Role findOne(Long id) {
        return roleRepository.findById(id).orElse(null);
    }
}
