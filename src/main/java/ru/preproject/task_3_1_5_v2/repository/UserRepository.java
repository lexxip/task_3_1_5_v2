package ru.preproject.task_3_1_5_v2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.preproject.task_3_1_5_v2.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String username);
}
