package com.techieplanet.applicationdevelopment.service;

import com.techieplanet.applicationdevelopment.dto.StudentStatistics;
import com.techieplanet.applicationdevelopment.model.StudentScore;
import com.techieplanet.applicationdevelopment.repository.StudentScoreRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentScoreServiceImplTest {

    @Mock
    private StudentScoreRepository studentScoreRepository;

    @InjectMocks
    private StudentScoreServiceImpl service;

    private static StudentScore scoreRow(
            int studentId, int s1, int s2, int s3, int s4, int s5
    ) {
        StudentScore sc = new StudentScore();
        sc.setStudentId(studentId);
        sc.setSubject1Score(BigDecimal.valueOf(s1));
        sc.setSubject2Score(BigDecimal.valueOf(s2));
        sc.setSubject3Score(BigDecimal.valueOf(s3));
        sc.setSubject4Score(BigDecimal.valueOf(s4));
        sc.setSubject5Score(BigDecimal.valueOf(s5));
        return sc;
    }


    @Test
    void testMeanCalculation() {
        List<BigDecimal> scores = Arrays.asList(
                new BigDecimal("80"),
                new BigDecimal("90"),
                new BigDecimal("70")
        );
        BigDecimal mean = service.calculateMean(scores);
        assertEquals(new BigDecimal("80"), mean);
    }

    @Test
    void testMedianCalculationOdd() {
        List<BigDecimal> scores = new ArrayList<>(List.of(
                new BigDecimal("70"),
                new BigDecimal("90"),
                new BigDecimal("80")
        ));

        assertEquals(new BigDecimal("80"), service.calculateMedian(scores));
    }

    @Test
    void testMedianCalculationEven() {
        List<BigDecimal> scores = new ArrayList<>(List.of(
                new BigDecimal("70"),
                new BigDecimal("90"),
                new BigDecimal("80"),
                new BigDecimal("60")
        ));

        assertEquals(new BigDecimal("75"), service.calculateMedian(scores));
    }

    @Test
    void testModeCalculation() {

        List<BigDecimal> scores = List.of(
                new BigDecimal("80"), new BigDecimal("90"), new BigDecimal("80"),
                new BigDecimal("70"), new BigDecimal("60"), new BigDecimal("80")
        );
        assertEquals(new BigDecimal("80"), service.calculateMode(scores));
    }

    @Test
    void getStudentStatistics_whenRepoEmpty_returnsZeros() {
        when(studentScoreRepository.findByStudentId(999)).thenReturn(Collections.emptyList());

        StudentStatistics stats = service.getStudentStatistics(999);

        assertEquals(999, stats.getStudentId());
        assertEquals(BigDecimal.ZERO, stats.getMeanScore());
        assertEquals(BigDecimal.ZERO, stats.getMedianScore());
        assertEquals(BigDecimal.ZERO, stats.getModeScore());
        verify(studentScoreRepository).findByStudentId(999);
    }

    @Test
    void getStudentStatistics_computesFromFlattenedFiveSubjectsPerRow() {
        List<StudentScore> rows = List.of(
                scoreRow(1, 80, 90, 70, 60, 85),
                scoreRow(1, 76, 92, 84, 88, 79)
        );
        when(studentScoreRepository.findByStudentId(1)).thenReturn(rows);

        StudentStatistics stats = service.getStudentStatistics(1);

        BigDecimal expectedMean = new BigDecimal("803").divide(new BigDecimal("10"), java.math.RoundingMode.HALF_UP);

        expectedMean = new BigDecimal("804").divide(new BigDecimal("10"), java.math.RoundingMode.HALF_UP);
        assertEquals(expectedMean, stats.getMeanScore());

        List<BigDecimal> flattened = new ArrayList<>();
        rows.forEach(r -> flattened.addAll(List.of(
                r.getSubject1Score(), r.getSubject2Score(), r.getSubject3Score(),
                r.getSubject4Score(), r.getSubject5Score()
        )));
        BigDecimal expectedMedian = service.calculateMedian(new ArrayList<>(flattened));
        assertEquals(expectedMedian, stats.getMedianScore());

        BigDecimal expectedMode = service.calculateMode(flattened);
        assertEquals(expectedMode, stats.getModeScore());
    }
}
