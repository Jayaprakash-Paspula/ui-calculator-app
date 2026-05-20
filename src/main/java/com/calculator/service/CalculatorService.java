package com.calculator.service;

import com.calculator.model.CalculationRequest;
import com.calculator.model.CalculationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service layer for calculator operations
 * Handles all business logic for calculations
 */
@Slf4j
@Service
public class CalculatorService {

    /**
     * Perform calculation based on operator
     *
     * @param request the calculation request containing two numbers and an operator
     * @return the calculation response with result or error message
     */
    public CalculationResponse calculate(CalculationRequest request) {
        log.debug("Starting calculation: {} {} {}",
                request.getFirstNumber(),
                request.getOperator(),
                request.getSecondNumber());

        // Validate input
        if (request.getFirstNumber() == null || request.getSecondNumber() == null) {
            log.error("Invalid input: Missing operand");
            return new CalculationResponse(
                    "Invalid input: Both operands must be provided",
                    request.getFirstNumber(),
                    request.getSecondNumber(),
                    request.getOperator()
            );
        }

        if (request.getOperator() == null || request.getOperator().trim().isEmpty()) {
            log.error("Invalid operator: Operator is required");
            return new CalculationResponse(
                    "Invalid operator: Operator is required",
                    request.getFirstNumber(),
                    request.getSecondNumber(),
                    request.getOperator()
            );
        }

        Double result;
        String operator = request.getOperator().trim();

        try {
            result = performOperation(
                    request.getFirstNumber(),
                    request.getSecondNumber(),
                    operator
            );

            log.info("Calculation successful: {} {} {} = {}",
                    request.getFirstNumber(),
                    operator,
                    request.getSecondNumber(),
                    result);

            return new CalculationResponse(
                    result,
                    operator,
                    request.getFirstNumber(),
                    request.getSecondNumber()
            );
        } catch (ArithmeticException e) {
            log.error("Arithmetic error: {}", e.getMessage());
            return new CalculationResponse(
                    e.getMessage(),
                    request.getFirstNumber(),
                    request.getSecondNumber(),
                    operator
            );
        } catch (IllegalArgumentException e) {
            log.error("Invalid operator: {}", e.getMessage());
            return new CalculationResponse(
                    e.getMessage(),
                    request.getFirstNumber(),
                    request.getSecondNumber(),
                    operator
            );
        }
    }

    /**
     * Perform the actual operation based on the operator
     *
     * @param firstNumber the first operand
     * @param secondNumber the second operand
     * @param operator the operation to perform (+, -, *, /)
     * @return the result of the operation
     * @throws ArithmeticException if division by zero
     * @throws IllegalArgumentException if invalid operator
     */
    private Double performOperation(Double firstNumber, Double secondNumber, String operator) {
        return switch (operator) {
            case "+" -> add(firstNumber, secondNumber);
            case "-" -> subtract(firstNumber, secondNumber);
            case "*" -> multiply(firstNumber, secondNumber);
            case "/" -> divide(firstNumber, secondNumber);
            default -> throw new IllegalArgumentException("Invalid operator: " + operator +
                                                         ". Valid operators are: +, -, *, /");
        };
    }

    /**
     * Add two numbers
     */
    public Double add(Double a, Double b) {
        log.debug("Adding: {} + {}", a, b);
        return a + b;
    }

    /**
     * Subtract two numbers
     */
    public Double subtract(Double a, Double b) {
        log.debug("Subtracting: {} - {}", a, b);
        return a - b;
    }

    /**
     * Multiply two numbers
     */
    public Double multiply(Double a, Double b) {
        log.debug("Multiplying: {} * {}", a, b);
        return a * b;
    }

    /**
     * Divide two numbers
     *
     * @throws ArithmeticException if divisor is zero
     */
    public Double divide(Double a, Double b) {
        log.debug("Dividing: {} / {}", a, b);
        if (b == 0.0) {
            log.error("Division by zero attempted");
            throw new ArithmeticException("Cannot divide by zero");
        }
        return a / b;
    }
}

