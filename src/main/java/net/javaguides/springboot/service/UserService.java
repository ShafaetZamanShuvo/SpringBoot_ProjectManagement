package net.javaguides.springboot.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import net.javaguides.springboot.model.User;
import net.javaguides.springboot.web.dto.UserRegistrationDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService extends UserDetailsService{
	User save(UserRegistrationDto registrationDto);

	List<User> getAllUsers();

	User getUserById(Long memberId);

	User getUserByEmail(String email);

	User getUserByUsername(String username);

    boolean findByUsername(String username);
}
