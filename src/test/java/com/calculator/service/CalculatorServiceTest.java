package com.calculator.service;

import com.calculator.model.CalculationRequest;
import com.calculator.model.CalculationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for CalculatorService
 */
public class CalculatorServiceTest {

    private CalculatorService calculatorService;

    @BeforeEach
    public void setUp() {
        calculatorService = new CalculatorService();
    }

    // Addition Tests
    @Test
    public void testAddPositiveNumbers() {
        CalculationRequest request = new CalculationRequest(5.0, 3.0, "+");
        CalculationResponse response = calculatorService.calculate(request);

        assertTrue(response.getSuccess());
        assertEquals(8.0, response.getResult());
    }

    @Test
    public void testAddNegativeNumbers() {
        CalculationRequest request = new CalculationRequest(-5.0, -3.0, "+");
        CalculationResponse response = calculatorService.calculate(request);

        assertTrue(response.getSuccess());
        assertEquals(-8.0, response.getResult());
    }

    @Test
    public void testAddMixedNumbers() {
        CalculationRequest request = new CalculationRequest(10.0, -3.0, "+");
        CalculationResponse response = calculatorService.calculate(request);

        assertTrue(response.getSuccess());
        assertEquals(7.0, response.getResult());
    }

    // Subtraction Tests
    @Test
    public void testSubtractPositiveNumbers() {
        CalculationRequest request = new CalculationRequest(10.0, 3.0, "-");
        CalculationResponse response = calculatorService.calculate(request);

        assertTrue(response.getSuccess());
        assertEquals(7.0, response.getResult());
    }

    @Test
    public void testSubtractNegativeResult() {
        CalculationRequest request = new CalculationRequest(3.0, 10.0, "-");
        CalculationResponse response = calculatorService.calculate(request);

        assertTrue(response.getSuccess());
        assertEquals(-7.0, response.getResult());
    }

    // Multiplication Tests
    @Test
    public void testMultiplyPositiveNumbers() {
        CalculationRequest request = new CalculationRequest(5.0, 3.0, "*");
        CalculationResponse response = calculatorService.calculate(request);

        assertTrue(response.getSuccess());
        assertEquals(15.0, response.getResult());
    }

    @Test
    public void testMultiplyByZero() {
        CalculationRequest request = new CalculationRequest(5.0, 0.0, "*");
        CalculationResponse response = calculatorService.calculate(request);

        assertTrue(response.getSuccess());
        assertEquals(0.0, response.getResult());
    }

    @Test
    public void testMultiplyNegativeNumbers() {
        CalculationRequest request = new CalculationRequest(-5.0, -3.0, "*");
        CalculationResponse response = calculatorService.calculate(request);

        assertTrue(response.getSuccess());
        assertEquals(15.0, response.getResult());
    }

    // Division Tests
    @Test
    public void testDividePositiveNumbers() {
        CalculationRequest request = new CalculationRequest(10.0, 2.0, "/");
        CalculationResponse response = calculatorService.calculate(request);

        assertTrue(response.getSuccess());
        assertEquals(5.0, response.getResult());
    }

    @Test
    public void testDivideByZero() {
        CalculationRequest request = new CalculationRequest(10.0, 0.0, "/");
        CalculationResponse response = calculatorService.calculate(request);

        assertFalse(response.getSuccess());
        assertNull(response.getResult());
        assertEquals("Cannot divide by zero", response.getErrorMessage());
    }

    @Test
    public void testDivideResultsInDecimal() {
        CalculationRequest request = new CalculationRequest(5.0, 2.0, "/");
        CalculationResponse response = calculatorService.calculate(request);

        assertTrue(response.getSuccess());
        assertEquals(2.5, response.getResult());
    }

    // Invalid Operator Tests
    @Test
    public void testInvalidOperator() {
        CalculationRequest request = new CalculationRequest(5.0, 3.0, "^");
        CalculationResponse response = calculatorService.calculate(request);

        assertFalse(response.getSuccess());
        assertNull(response.getResult());
        assertTrue(response.getErrorMessage().contains("Invalid operator"));
    }

    // Null Input Tests
    @Test
    public void testNullFirstNumber() {
        CalculationRequest request = new CalculationRequest(null, 5.0, "+");
        CalculationResponse response = calculatorService.calculate(request);

        assertFalse(response.getSuccess());
        assertTrue(response.getErrorMessage().contains("Invalid input"));
    }

    @Test
    public void testNullSecondNumber() {
        CalculationRequest request = new CalculationRequest(5.0, null, "+");
        CalculationResponse response = calculatorService.calculate(request);

        assertFalse(response.getSuccess());
        assertTrue(response.getErrorMessage().contains("Invalid input"));
    }

    @Test
    public void testNullOperator() {
        CalculationRequest request = new CalculationRequest(5.0, 3.0, null);
        CalculationResponse response = calculatorService.calculate(request);

        assertFalse(response.getSuccess());
        assertTrue(response.getErrorMessage().contains("Operator is required"));
    }

    // Direct method tests
    @Test
    public void testAddMethod() {
        double result = calculatorService.add(10.0, 5.0);
        assertEquals(15.0, result);
    }

    @Test
    public void testSubtractMethod() {
        double result = calculatorService.subtract(10.0, 3.0);
        assertEquals(7.0, result);
    }

    @Test
    public void testMultiplyMethod() {
        double result = calculatorService.multiply(4.0, 5.0);
        assertEquals(20.0, result);
    }

    @Test
    public void testDivideMethod() {
        double result = calculatorService.divide(20.0, 4.0);
        assertEquals(5.0, result);
    }

    @Test
    public void testDivideMethodByZero() {
        assertThrows(ArithmeticException.class, () -> {
            calculatorService.divide(10.0, 0.0);
        });
    }
}

