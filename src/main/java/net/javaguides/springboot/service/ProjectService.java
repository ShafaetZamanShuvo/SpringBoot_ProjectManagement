package net.javaguides.springboot.service;

import net.javaguides.springboot.model.Project;

import java.time.LocalDate;
import java.util.List;


public interface ProjectService {
    List<Project> getAllProjects();

    List<Project> getAllProjectsBystartDateAndEndDate(LocalDate startDate, LocalDate endDate);

    Project getProjectById(Long id);

    Project saveProject(Project project);

    void deleteProjectById(Long id);

    Project updateProject(Project project);
}
