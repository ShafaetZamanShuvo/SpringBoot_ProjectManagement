package net.javaguides.springboot.service;

import net.javaguides.springboot.model.Project;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    private ProjectService projectService;

    public ReportService(ProjectService projectService) {
        super();
        this.projectService = projectService;
    }

    public String getReport() throws FileNotFoundException, JRException {
        String path = "C:\\Users\\DELL\\Desktop\\Report";
        List<Project> projects = projectService.getAllProjects();
        File file = ResourceUtils.getFile("classpath:projects.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(projects);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Shafaet");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\Projects.pdf");

        return "report generated in path : " + path;

    }
}
