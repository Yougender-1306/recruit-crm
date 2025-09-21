package com.assessment.recruitcrm.assessment.controller;

import com.assessment.recruitcrm.assessment.dto.EmployeeProjectDTO;
import com.assessment.recruitcrm.assessment.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employee-projects")
public class EmployeeProjectController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public EmployeeProjectDTO createEmployeeProject(@RequestBody EmployeeProjectDTO employeeProjectDTO) {
        return employeeService.createEmployeeProject(employeeProjectDTO);
    }

    @PostMapping("/bulk")
    public List<EmployeeProjectDTO> createEmployeeProjectsBulk(@RequestBody List<EmployeeProjectDTO> employeeProjectDTOs) {
        return employeeProjectDTOs.stream()
                .map(employeeService::createEmployeeProject)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAllEmployeeProjects() {
        employeeService.deleteAllEmployeeProjects();
        return ResponseEntity.ok().build();
    }
}