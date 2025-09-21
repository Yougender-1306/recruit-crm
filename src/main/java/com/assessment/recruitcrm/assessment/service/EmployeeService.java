package com.assessment.recruitcrm.assessment.service;

import com.assessment.recruitcrm.assessment.dto.EmployeeDTO;
import com.assessment.recruitcrm.assessment.dto.DepartmentDTO;
import com.assessment.recruitcrm.assessment.dto.EmployeeProjectDTO;
import com.assessment.recruitcrm.assessment.dto.PerformanceReviewDTO;
import com.assessment.recruitcrm.assessment.dto.ProjectDTO;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    Page<EmployeeDTO> getEmployeesWithFilters(Integer score, LocalDate reviewDate, List<String> departments, List<String> projects, int page, int size);
    Optional<EmployeeDTO> getDetailedEmployee(Long id);
    EmployeeDTO createEmployee(EmployeeDTO employeeDTO);
    List<EmployeeDTO> createEmployeesBulk(List<EmployeeDTO> employeeDTOs);
    void deleteAllEmployees();
    DepartmentDTO createDepartment(DepartmentDTO departmentDTO);
    List<DepartmentDTO> getAllDepartments(); // Ensure this is present
    void deleteAllDepartments();
    PerformanceReviewDTO createPerformanceReview(PerformanceReviewDTO reviewDTO);
    void deleteAllPerformanceReviews();
    ProjectDTO createProject(ProjectDTO projectDTO);
    void deleteAllProjects();
    EmployeeProjectDTO createEmployeeProject(EmployeeProjectDTO employeeProjectDTO);
    void deleteAllEmployeeProjects();
}