package com.calculator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * Model class for calculator requests
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalculationRequest {
    private Double firstNumber;
    private Double secondNumber;
    private String operator;
    private LocalDateTime timestamp;

    public CalculationRequest(Double firstNumber, Double secondNumber, String operator) {
        this.firstNumber = firstNumber;
        this.secondNumber = secondNumber;
        this.operator = operator;
        this.timestamp = LocalDateTime.now();
    }
}

