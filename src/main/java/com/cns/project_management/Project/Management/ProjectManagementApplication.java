package com.cns.project_management.Project.Management;

import com.cns.project_management.Project.Management.entity.Project;
import com.cns.project_management.Project.Management.entity.User;
import com.cns.project_management.Project.Management.repository.ProjectRepository;
import com.cns.project_management.Project.Management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class ProjectManagementApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ProjectManagementApplication.class, args);
	}

	@Autowired
	private ProjectRepository projectRepository;
	//private UserRepository userRepository;
	@Override
	public void run(String... args) throws Exception {
 		 //name, password, email
//		User user1 = new User("John", "123", "john@gmail.com");
//		userRepository.save(user1);
		// public Project(String name, String intro, int status, Date startDate, Date endDate, User owner)
//		Date startDate = new Date(2022, 0, 1); // January 1, 2022
//		Date endDate = new Date(2022, 11, 31); // December 31, 2022
//
//		Project project1 = new Project("Project 1", "Introduction to Project 1", 1, startDate, endDate);
//		projectRepository.save(project1);

	}
}

