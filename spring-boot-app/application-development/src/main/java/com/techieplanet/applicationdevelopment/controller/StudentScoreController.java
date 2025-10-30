package com.techieplanet.applicationdevelopment.controller;

import com.techieplanet.applicationdevelopment.dto.StudentStatistics;
import com.techieplanet.applicationdevelopment.service.StudentScoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(name = "Student Scores", description = "Operations on student scores and statistics")
@RestController
@RequestMapping("/api/students")
public class StudentScoreController {

    private final StudentScoreService studentScoreService;

    public StudentScoreController(StudentScoreService studentScoreService) {
        this.studentScoreService = studentScoreService;
    }

    @Operation(summary = "Get statistics for a student",
            description = "Returns mean, median and mode for the student's subjects")
    @GetMapping("/statistics")
    public ResponseEntity<StudentStatistics> getStatistics(@RequestParam Integer studentId) {
        StudentStatistics stats = studentScoreService.getStudentStatistics(studentId);
        return ResponseEntity.ok(stats);
    }

    @Operation(summary = "Get statistics for a student and adds pagination",
            description = "Returns mean, median and mode for the student's subjects")
    @GetMapping("/statisticsWithPagination")
    public ResponseEntity<Page<StudentStatistics>> getStatisticsWithPagination(
            @RequestParam Optional<Integer> studentId,
            @ParameterObject Pageable pageable) {

        Page<StudentStatistics> page = studentScoreService.getStatisticsWithPagination(studentId, pageable);
        return ResponseEntity.ok(page);
    }
}

