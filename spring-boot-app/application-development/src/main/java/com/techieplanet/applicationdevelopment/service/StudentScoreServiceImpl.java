package com.techieplanet.applicationdevelopment.service;

import com.techieplanet.applicationdevelopment.dto.StudentStatistics;
import com.techieplanet.applicationdevelopment.model.StudentScore;
import com.techieplanet.applicationdevelopment.repository.StudentScoreRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class StudentScoreServiceImpl implements StudentScoreService {

    private static final int MAX_PAGE_SIZE = 100;

    private final StudentScoreRepository studentScoreRepository;

    public StudentScoreServiceImpl(StudentScoreRepository studentScoreRepository) {
        this.studentScoreRepository = studentScoreRepository;
    }

    @Override
    public BigDecimal calculateMean(List<BigDecimal> scores) {
        BigDecimal sum = scores.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        return sum.divide(BigDecimal.valueOf(scores.size()), RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal calculateMedian(List<BigDecimal> scores) {
        Collections.sort(scores);
        int size = scores.size();
        if (size % 2 == 0) {
            return (scores.get(size / 2 - 1).add(scores.get(size / 2))).divide(BigDecimal.valueOf(2), RoundingMode.HALF_UP);
        } else {
            return scores.get(size / 2);
        }
    }

    @Override
    public BigDecimal calculateMode(List<BigDecimal> scores) {
        Map<BigDecimal, Long> frequencyMap = scores.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        return frequencyMap.entrySet().stream()
                .max(Comparator.comparingLong(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse(BigDecimal.ZERO);
    }

    @Override
    public StudentStatistics getStudentStatistics(Integer studentId) {
        List<StudentScore> scores = studentScoreRepository.findByStudentId(studentId);
        if (scores.isEmpty()) return new StudentStatistics(studentId, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);

        List<BigDecimal> scoreList = scores.stream()
                .map(score -> Arrays.asList(score.getSubject1Score(), score.getSubject2Score(), score.getSubject3Score(), score.getSubject4Score(), score.getSubject5Score()))
                .flatMap(List::stream)
                .collect(Collectors.toList());

        BigDecimal mean = calculateMean(scoreList);
        BigDecimal median = calculateMedian(scoreList);
        BigDecimal mode = calculateMode(scoreList);

        return new StudentStatistics(studentId, mean, median, mode);
    }

    @Override
    public Page<StudentStatistics> getStatisticsWithPagination(Optional<Integer> studentId, Pageable pageable) {

        int cappedSize = Math.min(pageable.getPageSize(), MAX_PAGE_SIZE);
        Pageable capped = PageRequest.of(pageable.getPageNumber(), cappedSize, pageable.getSort());

        if (studentId.isPresent()) {

            StudentStatistics stats = getStudentStatistics(studentId.get());

            boolean exists = !studentScoreRepository.findByStudentId(studentId.get()).isEmpty();
            List<StudentStatistics> content = exists ? List.of(stats) : List.of();
            long total = exists ? 1 : 0;

            return new PageImpl<>(content, capped, total);
        }

        Page<Integer> ids = studentScoreRepository.findDistinctStudentIds(capped);
        List<StudentStatistics> content = ids.getContent()
                .stream()
                .map(this::getStudentStatistics)
                .toList();

        return new PageImpl<>(content, capped, ids.getTotalElements());
    }
}

