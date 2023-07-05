package com.cns.project_management.Project.Management.service;

import com.cns.project_management.Project.Management.controller.DTO.UserRegistrationDTO;
import com.cns.project_management.Project.Management.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User registerUser(UserRegistrationDTO userRegistrationDTO);
}
