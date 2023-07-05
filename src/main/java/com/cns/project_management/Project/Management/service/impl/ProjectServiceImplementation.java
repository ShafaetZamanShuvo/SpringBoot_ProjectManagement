package com.cns.project_management.Project.Management.service.impl;

import com.cns.project_management.Project.Management.entity.Project;
import com.cns.project_management.Project.Management.entity.User;
import com.cns.project_management.Project.Management.repository.ProjectRepository;
import com.cns.project_management.Project.Management.repository.UserRepository;
import com.cns.project_management.Project.Management.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectServiceImplementation implements ProjectService {

    private ProjectRepository projectRepository;
    private UserRepository userRepository;

    @Autowired
    public ProjectServiceImplementation(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Project> getAllProjects() {
        //return all the projects
        return projectRepository.findAll();
    }

    @Override
    public List<Project> getAllProjectsBystartDateAndEndDate(LocalDate startDate, LocalDate endDate) {
       List<Project> projectsByDate = new ArrayList<>();
         List<Project> projects = projectRepository.findAll();
         for(Project project: projects)
         {
             if(project.getStartDate().isAfter(startDate) && project.getEndDate().isBefore(endDate))
                 projectsByDate.add(project);
         }

            return projectsByDate;
    }

    @Override
    public Project getProjectById(Long id) {
        return projectRepository.findById(id).get();
    }

    @Override
    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public void deleteProjectById(Long id) {
        projectRepository.deleteById(id);
    }

    @Override
    public Project updateProject(Project project) {

        return projectRepository.save(project);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public List<User> getUsersByIds(List<Long> memberIds) {
        return userRepository.findAllById(memberIds);
    }
}
