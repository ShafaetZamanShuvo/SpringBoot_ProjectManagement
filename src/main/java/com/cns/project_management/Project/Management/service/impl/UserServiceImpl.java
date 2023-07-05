package com.cns.project_management.Project.Management.service.impl;

import com.cns.project_management.Project.Management.controller.DTO.UserRegistrationDTO;
import com.cns.project_management.Project.Management.entity.User;
import com.cns.project_management.Project.Management.repository.UserRepository;
import com.cns.project_management.Project.Management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers()
    {
        return userRepository.findAll();
    }

    @Override
    public User registerUser(UserRegistrationDTO userRegistrationDTO) {
        User user = new User();
        user.setUsername(userRegistrationDTO.getUsername());
        user.setPassword(userRegistrationDTO.getPassword());
        user.setEmail(userRegistrationDTO.getEmail());
        return userRepository.save(user);
    }



    public User getUserById(Long id)
    {
        return userRepository.findById(id).get();
    }

    public User getUserByUsername(String username)
    {
        return userRepository.findByUsername(username);
    }

}
