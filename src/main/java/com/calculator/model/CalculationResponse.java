package com.calculator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * Model class for calculator responses
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalculationResponse {
    private Double result;
    private String operation;
    private Double firstNumber;
    private Double secondNumber;
    private Boolean success;
    private String errorMessage;
    private LocalDateTime timestamp;

    /**
     * Constructor for successful calculations
     */
    public CalculationResponse(Double result, String operation, Double firstNumber, Double secondNumber) {
        this.result = result;
        this.operation = operation;
        this.firstNumber = firstNumber;
        this.secondNumber = secondNumber;
        this.success = true;
        this.errorMessage = null;
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Constructor for error responses
     */
    public CalculationResponse(String errorMessage, Double firstNumber, Double secondNumber, String operation) {
        this.result = null;
        this.operation = operation;
        this.firstNumber = firstNumber;
        this.secondNumber = secondNumber;
        this.success = false;
        this.errorMessage = errorMessage;
        this.timestamp = LocalDateTime.now();
    }
}

