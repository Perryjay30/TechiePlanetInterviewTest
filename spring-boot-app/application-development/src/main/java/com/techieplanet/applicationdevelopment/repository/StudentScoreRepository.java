package com.techieplanet.applicationdevelopment.repository;

import com.techieplanet.applicationdevelopment.model.StudentScore;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentScoreRepository extends JpaRepository<StudentScore, Long> {
    List<StudentScore> findByStudentId(Integer studentId);

    @Query(
            value = "select distinct s.studentId from StudentScore s",
            countQuery = "select count(distinct s.studentId) from StudentScore s"
    )
    Page<Integer> findDistinctStudentIds(Pageable pageable);
}

