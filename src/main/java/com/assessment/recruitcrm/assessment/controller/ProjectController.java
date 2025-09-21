package com.assessment.recruitcrm.assessment.controller;

import com.assessment.recruitcrm.assessment.dto.ProjectDTO;
import com.assessment.recruitcrm.assessment.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ProjectDTO createProject(@RequestBody ProjectDTO projectDTO) {
        return employeeService.createProject(projectDTO);
    }

    @PostMapping("/bulk")
    public List<ProjectDTO> createProjectsBulk(@RequestBody List<ProjectDTO> projectDTOs) {
        return projectDTOs.stream()
                .map(employeeService::createProject)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAllProjects() {
        employeeService.deleteAllProjects();
        return ResponseEntity.ok().build();
    }
}