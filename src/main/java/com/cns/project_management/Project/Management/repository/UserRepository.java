package com.cns.project_management.Project.Management.repository;

import com.cns.project_management.Project.Management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findByUsername(String username);

    public User getUserById(Long id);
}
