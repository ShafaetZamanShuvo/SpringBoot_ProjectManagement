package net.javaguides.springboot.web;

import net.javaguides.springboot.model.User;
import net.javaguides.springboot.service.UserService;
import net.javaguides.springboot.web.dto.UserRegistrationDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class UserController {

	private UserService userService;

	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}


	@GetMapping("/")
	public String home() {
		return "redirect:/projects";
	}

	@GetMapping("/login")
	public String loginForm( Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "login";
	}

	@PostMapping("/login")
	public String loginPost() {
		return "redirect:/projects";
	}

	@PostMapping("/logout")
	public String logoutPost() {
		return "redirect:/login?logout";
	}


	@ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }
	
	@GetMapping("/registration")
	public String showRegistrationForm() {
		return "registration";
	}
	
	@PostMapping("/registration")
	public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto registrationDto) {
		//check if username or email already exists

		String username = registrationDto.getUsername();
		String email = registrationDto.getEmail();

		if (userService.findByUsername(username)) {
			return "redirect:/registration?username";
		}

		if (userService.getUserByEmail(email) != null) {
			return "redirect:/registration?email";
		}

		userService.save(registrationDto);
		return "redirect:/registration?success";
	}
}
