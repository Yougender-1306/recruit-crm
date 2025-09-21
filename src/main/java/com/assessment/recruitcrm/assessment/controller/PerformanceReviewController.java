package com.assessment.recruitcrm.assessment.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assessment.recruitcrm.assessment.dto.PerformanceReviewDTO;
import com.assessment.recruitcrm.assessment.service.EmployeeService;

@RestController
@RequestMapping("/performance-reviews")
public class PerformanceReviewController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public PerformanceReviewDTO createPerformanceReview(@RequestBody PerformanceReviewDTO reviewDTO) {
        return employeeService.createPerformanceReview(reviewDTO);
    }

    @PostMapping("/bulk")
    public List<PerformanceReviewDTO> createPerformanceReviewsBulk(@RequestBody List<PerformanceReviewDTO> reviewDTOs) {
        return reviewDTOs.stream()
                .map(employeeService::createPerformanceReview)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAllPerformanceReviews() {
        employeeService.deleteAllPerformanceReviews();
        return ResponseEntity.ok().build();
    }
}