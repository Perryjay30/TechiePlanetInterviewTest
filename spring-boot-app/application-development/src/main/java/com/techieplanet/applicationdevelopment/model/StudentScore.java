package com.techieplanet.applicationdevelopment.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer studentId;
    private BigDecimal subject1Score;
    private BigDecimal subject2Score;
    private BigDecimal subject3Score;
    private BigDecimal subject4Score;
    private BigDecimal subject5Score;

}

