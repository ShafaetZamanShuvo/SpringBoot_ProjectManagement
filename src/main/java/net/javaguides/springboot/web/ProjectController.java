package net.javaguides.springboot.web;

import net.javaguides.springboot.model.Project;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.service.ProjectService;
import net.javaguides.springboot.service.ReportService;
import net.javaguides.springboot.service.UserService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    private ProjectService projectService;
    private UserService userService;
    private ReportService reportService;

    public ProjectController(ProjectService projectService, UserService userService, ReportService reportService) {
        super();
        this.projectService = projectService;
        this.userService = userService;
        this.reportService = reportService;
    }

    @GetMapping // GET /projects
        public String listProjects(Model model) {
            model.addAttribute("projects", projectService.getAllProjects());
            return "projects";
        }
    @GetMapping("/new") // GET /projects/new
        public String createProjectForm(Model model) {
            // create project object to hold project form data
            Project project = new Project();
            model.addAttribute("project", project);
            model.addAttribute("users", userService.getAllUsers());
            return "new-project";
        }

    @PostMapping("/projects") // POST /projects
        public String saveProject(Project project, Model model) {
            // save project to database
            projectService.saveProject(project);
            return "redirect:/projects";
        }

    @PostMapping
    public String saveProject(@ModelAttribute("project") Project project,
                              @RequestParam(name = "memberIds", required = false) ArrayList<Long> memberIds) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        System.out.println(currentPrincipalName);
        User loggedInUser = userService.getUserByEmail(currentPrincipalName);
        project.setOwner(loggedInUser);
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
        System.out.println(memberIds);
        if (memberIds != null) {
            for (Long memberId : memberIds) {
                User member = userService.getUserById(memberId);
                member.getProjects().add(project);
                members.add(member);
            }
        }else {
            project.setMembers(null);
        }
        project.setMembers(members);
        projectService.saveProject(project);

        return "redirect:/projects";
    }

    //show projects by date
    @GetMapping("/date")
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

    //see project details
    @GetMapping("/details/{id}")
    public String projectDetails(@PathVariable Long id, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        //System.out.println(currentPrincipalName);
        User loggedInUser = userService.getUserByEmail(currentPrincipalName);
        User owner = projectService.getProjectById(id).getOwner();

        //check if the logged in user is the owner of the project
        if (loggedInUser.getId() == owner.getId()) {
            model.addAttribute("isOwner", true);
        } else {
            model.addAttribute("isOwner", false);
        }

        //send the members list to the view
        List<User> members = projectService.getProjectById(id).getMembers();
        model.addAttribute("members", members);
        model.addAttribute("project", projectService.getProjectById(id));


        return "project-details";
    }

    //update project
    @GetMapping("/edit/{id}")
    public String editProjectForm(@PathVariable Long id, Model model) {
        model.addAttribute("project", projectService.getProjectById(id));
        List<User> members = projectService.getProjectById(id).getMembers();
        model.addAttribute("members", members);
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "update-project";
    }

    @PostMapping("/{id}")
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

        return "redirect:/projects/details/" + id+"?success";
    }

    @GetMapping("/{id}/addMember")
    public String addMemberForm(@PathVariable Long id, Model model) {
        //change the type of id from string to Long

        Long projectId = Long.parseLong(String.valueOf(id));
        System.out.println(projectId);
        model.addAttribute("project", projectService.getProjectById(projectId));
        model.addAttribute("users", userService.getAllUsers());
        List<User> members = projectService.getProjectById(projectId).getMembers();
        model.addAttribute("members", members);
        return "add-member";
    }

    @PostMapping("/{id}/addMember")
    public String addMember(@PathVariable Long id,
                            @RequestParam(name = "added-members", required = false) ArrayList<Long> memberIds) {
        Project project = projectService.getProjectById(id);
        System.out.println("Project name");
        System.out.println(project.getName());
        System.out.println("Member ids");
        System.out.println(memberIds);
        List<User> members = project.getMembers();
        if (memberIds != null) {
            for (Long memberId : memberIds) {
                User member = userService.getUserById(memberId);
                member.getProjects().add(project);
                members.add(member);
            }
        }
        project.setMembers(members);
        projectService.saveProject(project);

        return "redirect:/projects/edit/" + id;
    }

    @GetMapping("/{id}/removeMember")
    public String removeMemberForm(@PathVariable Long id, Model model) {
        System.out.println(id);
        model.addAttribute("project", projectService.getProjectById(id));
        List<User> members = projectService.getProjectById(id).getMembers();
        model.addAttribute("members", members);
        return "remove-member";
    }



    @PostMapping("/{id}/removeMember")
    public String removeMember(@PathVariable Long id,
                               @RequestParam(name = "removed-members", required = false) ArrayList<Long> memberIds) {
        Project project = projectService.getProjectById(id);
        List<User> members = project.getMembers();
        if (memberIds != null) {
            for (Long memberId : memberIds) {
                User member = userService.getUserById(memberId);
                member.getProjects().remove(project);
                members.remove(member);
            }
        }
        project.setMembers(members);
        projectService.saveProject(project);

        return "redirect:/projects/edit/" + id;
    }

    @GetMapping("/{id}")
    public String deleteProject(@PathVariable Long id) {
        this.projectService.deleteProjectById(id);
        return "redirect:/projects";
    }

        @GetMapping("/generateReport")
        public String generateReport() throws JRException, FileNotFoundException {
            return reportService.getReport();

        }




}
