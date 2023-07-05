package com.cns.project_management.Project.Management.service;

import com.cns.project_management.Project.Management.entity.Project;
import com.cns.project_management.Project.Management.entity.User;

import java.time.LocalDate;
import java.util.List;

public interface ProjectService {

    List<Project> getAllProjects();

    List<Project> getAllProjectsBystartDateAndEndDate(LocalDate startDate, LocalDate endDate);

    Project getProjectById(Long id);

    Project saveProject(Project project);

    void deleteProjectById(Long id);

    Project updateProject(Project project);

    List<User> getAllUsers();

    User getUserById(Long id);

    List<User> getUsersByIds(List<Long> memberIds);
}
