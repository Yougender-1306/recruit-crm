package com.assessment.recruitcrm.assessment.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.assessment.recruitcrm.assessment.dto.EmployeeDTO;
import com.assessment.recruitcrm.assessment.service.EmployeeService;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public Page<EmployeeDTO> getEmployees(
            @RequestParam(required = false) Integer score,
            @RequestParam(required = false) LocalDate reviewDate,
            @RequestParam(required = false) List<String> departments,
            @RequestParam(required = false) List<String> projects,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return employeeService.getEmployeesWithFilters(score, reviewDate, departments, projects, page, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getDetailedEmployee(@PathVariable Long id) {
        return employeeService.getDetailedEmployee(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public EmployeeDTO createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return employeeService.createEmployee(employeeDTO);
    }

    @PostMapping("/bulk")
    public List<EmployeeDTO> createEmployeesBulk(@RequestBody List<EmployeeDTO> employeeDTOs) {
        return employeeDTOs.stream()
                .map(employeeService::createEmployee)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAllEmployees() {
        employeeService.deleteAllEmployees();
        return ResponseEntity.ok().build();
    }
}