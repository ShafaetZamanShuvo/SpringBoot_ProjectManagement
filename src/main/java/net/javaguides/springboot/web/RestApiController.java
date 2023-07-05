package net.javaguides.springboot.web;

import net.javaguides.springboot.model.Project;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.service.ProjectService;
import net.javaguides.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequestMapping("/api")
public class RestApiController {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private UserService userService;

    @GetMapping("/v1/projects")
    @ResponseBody
    public List<Project> getProjects()
    {
        List<Project> projects =  projectService.getAllProjects();
        return projects;
    }

    @GetMapping("/v1/users")
    @ResponseBody
    public List<User> getUsers()
    {
        List<User> users =  userService.getAllUsers();
        return users;
    }

}