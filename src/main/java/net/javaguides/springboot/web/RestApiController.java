package net.javaguides.springboot.web;

import net.javaguides.springboot.model.Project;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.service.ProjectService;
import net.javaguides.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.AttributedString;
import java.util.List;

@Controller
@RequestMapping("/api")
public class RestApiController {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private UserService userService;

    @GetMapping(value = "/v1/projects", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Project> getProjects()
    {
        List<Project> projects = projectService.getAllProjects();
        return projects;
    }

    @GetMapping(value = "/v1/users")
    public String getUsers(Model model)
    {
        model.addAttribute("users", userService.getAllUsers());
        return "api-projects";
    }

//    public List<User> getUsers()
//    {
//        List<User> users =  userService.getAllUsers();
//        return users;
//    }


}
