package com.assessment.recruitcrm.assessment.controller;

import com.assessment.recruitcrm.assessment.dto.DepartmentDTO;
import com.assessment.recruitcrm.assessment.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public List<DepartmentDTO> getAllDepartments() {
        return employeeService.getAllDepartments();
    }

    @PostMapping
    public DepartmentDTO createDepartment(@RequestBody DepartmentDTO departmentDTO) {
        return employeeService.createDepartment(departmentDTO);
    }

    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAllDepartments() {
        employeeService.deleteAllDepartments();
        return ResponseEntity.ok().build();
    }
}