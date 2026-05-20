package com.calculator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * Model class for storing calculation history
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalculationHistory {
    private Double firstNumber;
    private Double secondNumber;
    private String operator;
    private Double result;
    private Boolean success;
    private String errorMessage;
    private LocalDateTime timestamp;

    /**
     * Create history entry from calculation request and response
     */
    public CalculationHistory(CalculationRequest request, CalculationResponse response) {
        this.firstNumber = request.getFirstNumber();
        this.secondNumber = request.getSecondNumber();
        this.operator = request.getOperator();
        this.result = response.getResult();
        this.success = response.getSuccess();
        this.errorMessage = response.getErrorMessage();
        this.timestamp = LocalDateTime.now();
    }
}

