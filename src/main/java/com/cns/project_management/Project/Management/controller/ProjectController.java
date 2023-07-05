package com.cns.project_management.Project.Management.controller;

import com.cns.project_management.Project.Management.entity.Project;
import com.cns.project_management.Project.Management.entity.User;
import com.cns.project_management.Project.Management.service.ProjectService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProjectController {

    private ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    //show project list
    @GetMapping("/projects")
    public String listProjects(Model model) {
        model.addAttribute("projects", projectService.getAllProjects());
        return "projects";
    }

    //create project
    @GetMapping("/projects/new")
    public String createProjectForm(Model model){
        //create project object to hold project form data
        Project project = new Project();
        model.addAttribute("project", project);
        //also send the users list to the view
        model.addAttribute("users", projectService.getAllUsers());
        return "createProject";
    }
    @PostMapping("/projects")
    public String saveProject(@ModelAttribute("project") Project project,
                              @RequestParam(name = "memberIds", required = false) ArrayList<Long> memberIds, HttpSession session) {
        // Set project status based on start and end dates
        //retrieve logged in user from session
        User user = (User) session.getAttribute("loggedInUser");
        //set project Owner
        project.setOwner(user);
        LocalDate currentDate = LocalDate.now();
        if (project.getStartDate() == null) {
            project.setStatus(0);
        } else if (project.getEndDate() != null && project.getEndDate().isBefore(currentDate)) {
            project.setStatus(3);
        } else if (project.getStartDate().isAfter(currentDate)) {
            project.setStatus(0);
        } else {
            project.setStatus(1);
        }
        // Set the selected members based on the provided memberIds
        List<User> members = new ArrayList<>();
        if (memberIds != null) {
            for (Long memberId : memberIds) {
                members.add(projectService.getUserById(memberId));
            }
        }
        if (members.isEmpty()) {
            project.setMembers(null);
        } else
        project.setMembers(members);
        projectService.saveProject(project);

        return "redirect:/projects";
    }
    //see project details
    @GetMapping("/projects/details/{id}")
    public String projectDetails(@PathVariable Long id, Model model, HttpSession session) {

        //retrieve logged in user from session
        User user = (User) session.getAttribute("loggedInUser");
        //retrieve project owner
        User owner = projectService.getProjectById(id).getOwner();

        //check if the logged in user is the owner of the project
        if (user.getId() == owner.getId()) {
            model.addAttribute("isOwner", true);
        } else {
            model.addAttribute("isOwner", false);
        }

        //send the members list to the view
        List<User> members = projectService.getProjectById(id).getMembers();
        model.addAttribute("members", members);
        model.addAttribute("project", projectService.getProjectById(id));

        return "projectDetails";
    }



    //update project
    @GetMapping("/projects/edit/{id}")
    public String editProjectForm(@PathVariable Long id, Model model) {
        model.addAttribute("project", projectService.getProjectById(id));
        return "updateProject";
    }

    @PostMapping("/projects/{id}")
    public String updateProject(@PathVariable Long id,
                                @ModelAttribute("project") Project project) {

        Project existingProject = projectService.getProjectById(id);
        existingProject.setId(project.getId());
        existingProject.setName(project.getName());
        existingProject.setIntro(project.getIntro());
        existingProject.setStatus(project.calculateStatus());
        existingProject.setStartDate(project.getStartDate());
        existingProject.setEndDate(project.getEndDate());
        projectService.updateProject(existingProject);

        return "redirect:/projects";
    }

    //delete project
    @GetMapping("/projects/{id}")
    public String deleteProject(@PathVariable Long id) {
        this.projectService.deleteProjectById(id);
        return "redirect:/projects";
    }

    //show projects by date
    @GetMapping("/projects/date")
    public String listProjectsByDate(@RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                     @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                                     Model model) {

        System.out.println(startDate);
        System.out.println(endDate);

        model.addAttribute("projects", projectService.getAllProjectsBystartDateAndEndDate(startDate, endDate));
        List<Project> projects = projectService.getAllProjectsBystartDateAndEndDate(startDate, endDate);

        if(projects.isEmpty()){
            model.addAttribute("message", "No projects found");
            System.out.println("No projects found");
        }
        else{
            for (Project project : projects) {

                System.out.println(project.getName());
            }

        }



        return "projects";
    }

    }
