package com.cns.project_management.Project.Management.repository;

import com.cns.project_management.Project.Management.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {


    List<Project> findAllByStartDateAndEndDate(LocalDate startDate, LocalDate endDate);
}
