package com.techieplanet.applicationdevelopment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentStatistics {

    private Integer studentId;
    private BigDecimal meanScore;
    private BigDecimal medianScore;
    private BigDecimal modeScore;

}

