package net.javaguides.springboot.service;

import net.javaguides.springboot.model.Project;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    private ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public List<Project> getAllProjectsBystartDateAndEndDate(LocalDate startDate, LocalDate endDate) {
        List<Project> projectsByDate = new ArrayList<>();
        List<Project> projects = projectRepository.findAll();
        for(Project project: projects)
        {
            if((project.getStartDate().isAfter(startDate) || project.getStartDate().isEqual(startDate) ) && (project.getEndDate().isBefore(endDate) || project.getStartDate().isEqual(endDate)))
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
        //first remove the project from the user's project list
        Project project = projectRepository.findById(id).get();
        project.getOwner().getProjects().remove(project);
        List<User> members = project.getMembers();
        for(User member: members)
        {
            member.getProjects().remove(project);
        }


        projectRepository.deleteById(id);
    }

    @Override
    public Project updateProject(Project project) {
        return projectRepository.save(project);
    }
}
