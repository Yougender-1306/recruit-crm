package com.assessment.recruitcrm.assessment.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.assessment.recruitcrm.assessment.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("SELECT e FROM Employee e " +
           "LEFT JOIN e.performanceReviews pr " +
           "LEFT JOIN e.department d " +
           "LEFT JOIN e.employeeProjects ep " +
           "LEFT JOIN ep.project p " +
           "WHERE (:reviewDate IS NULL OR pr.reviewDate = :reviewDate) " +
           "AND (:score IS NULL OR pr.score = :score) " +
           "AND (:departments IS NULL OR d.name IN :departments) " +
           "AND (:projects IS NULL OR p.name IN :projects) " +
           "GROUP BY e.id")
    Page<Employee> findWithFilters(@Param("score") Integer score,
                                  @Param("reviewDate") LocalDate reviewDate,
                                  @Param("departments") List<String> departments,
                                  @Param("projects") List<String> projects,
                                  Pageable pageable);
}
