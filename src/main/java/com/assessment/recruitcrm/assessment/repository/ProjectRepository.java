package com.assessment.recruitcrm.assessment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assessment.recruitcrm.assessment.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
}
