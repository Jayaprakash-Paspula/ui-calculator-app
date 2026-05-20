package com.calculator.service;

import com.calculator.model.CalculationHistory;
import com.calculator.model.CalculationRequest;
import com.calculator.model.CalculationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for HistoryService
 */
public class HistoryServiceTest {

    private HistoryService historyService;
    private CalculatorService calculatorService;

    @BeforeEach
    public void setUp() {
        historyService = new HistoryService();
        calculatorService = new CalculatorService();
    }

    @Test
    public void testAddToHistory() {
        CalculationRequest request = new CalculationRequest(5.0, 3.0, "+");
        CalculationResponse response = calculatorService.calculate(request);

        historyService.addToHistory(request, response);

        assertEquals(1, historyService.getHistorySize());
    }

    @Test
    public void testGetHistory() {
        CalculationRequest request1 = new CalculationRequest(5.0, 3.0, "+");
        CalculationResponse response1 = calculatorService.calculate(request1);
        historyService.addToHistory(request1, response1);

        CalculationRequest request2 = new CalculationRequest(10.0, 2.0, "/");
        CalculationResponse response2 = calculatorService.calculate(request2);
        historyService.addToHistory(request2, response2);

        List<CalculationHistory> history = historyService.getHistory();

        assertEquals(2, history.size());
        assertEquals(8.0, history.get(0).getResult());
        assertEquals(5.0, history.get(1).getResult());
    }

    @Test
    public void testGetLastNCalculations() {
        // Add 5 calculations
        for (int i = 1; i <= 5; i++) {
            CalculationRequest request = new CalculationRequest((double) i, 2.0, "+");
            CalculationResponse response = calculatorService.calculate(request);
            historyService.addToHistory(request, response);
        }

        List<CalculationHistory> lastThree = historyService.getLastNCalculations(3);

        assertEquals(3, lastThree.size());
    }

    @Test
    public void testGetLastNCalculationsMoreThanExists() {
        CalculationRequest request = new CalculationRequest(5.0, 3.0, "+");
        CalculationResponse response = calculatorService.calculate(request);
        historyService.addToHistory(request, response);

        List<CalculationHistory> lastTen = historyService.getLastNCalculations(10);

        assertEquals(1, lastTen.size());
    }

    @Test
    public void testClearHistory() {
        CalculationRequest request = new CalculationRequest(5.0, 3.0, "+");
        CalculationResponse response = calculatorService.calculate(request);
        historyService.addToHistory(request, response);

        assertEquals(1, historyService.getHistorySize());

        historyService.clearHistory();

        assertEquals(0, historyService.getHistorySize());
    }

    @Test
    public void testInvalidNParameter() {
        assertThrows(IllegalArgumentException.class, () -> {
            historyService.getLastNCalculations(-1);
        });
    }

    @Test
    public void testInvalidZeroParameter() {
        assertThrows(IllegalArgumentException.class, () -> {
            historyService.getLastNCalculations(0);
        });
    }
}

