package com.techieplanet.applicationdevelopment.service;

import com.techieplanet.applicationdevelopment.dto.StudentStatistics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface StudentScoreService {

    BigDecimal calculateMean(List<BigDecimal> scores);

    BigDecimal calculateMedian(List<BigDecimal> scores);

    BigDecimal calculateMode(List<BigDecimal> scores);

    StudentStatistics getStudentStatistics(Integer studentId);

    Page<StudentStatistics> getStatisticsWithPagination(Optional<Integer> studentId, Pageable pageable);
}
