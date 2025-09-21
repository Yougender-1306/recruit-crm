package com.assessment.recruitcrm.assessment.service.implementation;

import com.assessment.recruitcrm.assessment.dto.EmployeeDTO;
import com.assessment.recruitcrm.assessment.dto.DepartmentDTO;
import com.assessment.recruitcrm.assessment.dto.EmployeeProjectDTO;
import com.assessment.recruitcrm.assessment.dto.PerformanceReviewDTO;
import com.assessment.recruitcrm.assessment.dto.ProjectDTO;
import com.assessment.recruitcrm.assessment.entity.*;
import com.assessment.recruitcrm.assessment.repository.*;
import com.assessment.recruitcrm.assessment.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private PerformanceReviewRepository performanceReviewRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EmployeeProjectRepository employeeProjectRepository;

    @Override
    public Page<EmployeeDTO> getEmployeesWithFilters(Integer score, LocalDate reviewDate, List<String> departments, List<String> projects, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
        Page<Employee> employees = employeeRepository.findWithFilters(score, reviewDate, departments, projects, pageable);
        return employees.map(this::mapToDTO);
    }

    @Override
    public Optional<EmployeeDTO> getDetailedEmployee(Long id) {
        return employeeRepository.findById(id).map(this::mapToDetailedDTO);
    }

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setDateOfJoining(employeeDTO.getDateOfJoining());
        employee.setSalary(employeeDTO.getSalary());
        if (employeeDTO.getDepartmentId() != null) {
            employee.setDepartment(departmentRepository.findById(employeeDTO.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found")));
        }
        if (employeeDTO.getManagerId() != null) {
            employee.setManager(employeeRepository.findById(employeeDTO.getManagerId())
                    .orElseThrow(() -> new RuntimeException("Manager not found")));
        }
        Employee savedEmployee = employeeRepository.save(employee);
        return mapToDTO(savedEmployee);
    }

    @Override
    public List<EmployeeDTO> createEmployeesBulk(List<EmployeeDTO> employeeDTOs) {
        return employeeDTOs.stream().map(this::createEmployee).collect(Collectors.toList());
    }

    @Override
    public void deleteAllEmployees() {
        employeeRepository.deleteAll();
    }

    private EmployeeDTO mapToDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setEmail(employee.getEmail());
        dto.setDepartmentName(employee.getDepartment() != null ? employee.getDepartment().getName() : null);
        dto.setDateOfJoining(employee.getDateOfJoining());
        dto.setSalary(employee.getSalary());
        dto.setManagerName(employee.getManager() != null ? employee.getManager().getName() : null);
        return dto;
    }

    private EmployeeDTO mapToDetailedDTO(Employee employee) {
        EmployeeDTO dto = mapToDTO(employee);
        if (employee.getEmployeeProjects() != null) {
            dto.setProjects(employee.getEmployeeProjects().stream().map(ep -> {
                ProjectDTO pDTO = new ProjectDTO();
                pDTO.setId(ep.getProject().getId());
                pDTO.setName(ep.getProject().getName());
                pDTO.setStartDate(ep.getProject().getStartDate());
                pDTO.setEndDate(ep.getProject().getEndDate());
                pDTO.setDepartmentId(ep.getProject().getDepartment() != null ? ep.getProject().getDepartment().getId() : null);
                return pDTO;
            }).collect(Collectors.toList()));
        }
        if (employee.getPerformanceReviews() != null) {
            List<PerformanceReview> reviews = employee.getPerformanceReviews().stream()
                    .sorted((r1, r2) -> r2.getReviewDate().compareTo(r1.getReviewDate()))
                    .limit(3)
                    .collect(Collectors.toList());
            dto.setLastThreeReviews(reviews.stream().map(this::mapToReviewDTO).collect(Collectors.toList()));
        }
        return dto;
    }

    private PerformanceReviewDTO mapToReviewDTO(PerformanceReview review) {
        PerformanceReviewDTO dto = new PerformanceReviewDTO();
        dto.setId(review.getId());
        dto.setReviewDate(review.getReviewDate());
        dto.setScore(review.getScore());
        dto.setReviewComments(review.getReviewComments());
        return dto;
    }

    @Override
    public DepartmentDTO createDepartment(DepartmentDTO departmentDTO) {
        Department department = new Department();
        department.setName(departmentDTO.getName());
        department.setBudget(departmentDTO.getBudget());
        Department savedDepartment = departmentRepository.save(department);
        return mapToDepartmentDTO(savedDepartment);
    }

    @Override
    public List<DepartmentDTO> getAllDepartments() {
        return departmentRepository.findAll().stream()
                .map(this::mapToDepartmentDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAllDepartments() {
        departmentRepository.deleteAll();
    }

    private DepartmentDTO mapToDepartmentDTO(Department department) {
        DepartmentDTO dto = new DepartmentDTO();
        dto.setId(department.getId());
        dto.setName(department.getName());
        dto.setBudget(department.getBudget());
        return dto;
    }

    @Override
    public PerformanceReviewDTO createPerformanceReview(PerformanceReviewDTO reviewDTO) {
        PerformanceReview review = new PerformanceReview();
        review.setReviewDate(reviewDTO.getReviewDate());
        review.setScore(reviewDTO.getScore());
        review.setReviewComments(reviewDTO.getReviewComments());
        review.setEmployee(employeeRepository.findById(reviewDTO.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found")));
        PerformanceReview savedReview = performanceReviewRepository.save(review);
        return mapToReviewDTO(savedReview);
    }

    @Override
    public void deleteAllPerformanceReviews() {
        performanceReviewRepository.deleteAll();
    }

    @Override
    public ProjectDTO createProject(ProjectDTO projectDTO) {
        Project project = new Project();
        project.setName(projectDTO.getName());
        project.setStartDate(projectDTO.getStartDate());
        project.setEndDate(projectDTO.getEndDate());
        if (projectDTO.getDepartmentId() != null) {
            project.setDepartment(departmentRepository.findById(projectDTO.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found")));
        }
        Project savedProject = projectRepository.save(project);
        return mapToProjectDTO(savedProject);
    }

    @Override
    public void deleteAllProjects() {
        projectRepository.deleteAll();
    }

    private ProjectDTO mapToProjectDTO(Project project) {
        ProjectDTO dto = new ProjectDTO();
        dto.setId(project.getId());
        dto.setName(project.getName());
        dto.setStartDate(project.getStartDate());
        dto.setEndDate(project.getEndDate());
        dto.setDepartmentId(project.getDepartment() != null ? project.getDepartment().getId() : null);
        return dto;
    }

    @Override
    public EmployeeProjectDTO createEmployeeProject(EmployeeProjectDTO employeeProjectDTO) {
        EmployeeProject employeeProject = new EmployeeProject();
        employeeProject.setAssignedDate(employeeProjectDTO.getAssignedDate());
        employeeProject.setRole(employeeProjectDTO.getRole());
        employeeProject.setEmployee(employeeRepository.findById(employeeProjectDTO.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found")));
        employeeProject.setProject(projectRepository.findById(employeeProjectDTO.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found")));
        EmployeeProject savedEmployeeProject = employeeProjectRepository.save(employeeProject);
        return mapToEmployeeProjectDTO(savedEmployeeProject);
    }

    @Override
    public void deleteAllEmployeeProjects() {
        employeeProjectRepository.deleteAll();
    }

    private EmployeeProjectDTO mapToEmployeeProjectDTO(EmployeeProject employeeProject) {
        EmployeeProjectDTO dto = new EmployeeProjectDTO();
        dto.setId(employeeProject.getId());
        dto.setEmployeeId(employeeProject.getEmployee().getId());
        dto.setProjectId(employeeProject.getProject().getId());
        dto.setAssignedDate(employeeProject.getAssignedDate());
        dto.setRole(employeeProject.getRole());
        return dto;
    }
}