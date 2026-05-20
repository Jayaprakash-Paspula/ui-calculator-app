package com.calculator.controller;

import com.calculator.model.CalculationHistory;
import com.calculator.model.CalculationRequest;
import com.calculator.model.CalculationResponse;
import com.calculator.service.CalculatorService;
import com.calculator.service.HistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST Controller for calculator operations
 * Provides endpoints for performing calculations and managing history
 */
@Slf4j
@RestController
@RequestMapping("/api/calculator")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CalculatorController {

    @Autowired
    private CalculatorService calculatorService;

    @Autowired
    private HistoryService historyService;

    /**
     * Perform a calculation
     * POST /api/calculator/calculate
     *
     * @param request the calculation request
     * @return the calculation response
     */
    @PostMapping("/calculate")
    public ResponseEntity<CalculationResponse> calculate(@RequestBody CalculationRequest request) {
        log.info("Received calculation request: {} {} {}",
                request.getFirstNumber(),
                request.getOperator(),
                request.getSecondNumber());

        try {
            CalculationResponse response = calculatorService.calculate(request);
            historyService.addToHistory(request, response);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error processing calculation", e);
            CalculationResponse errorResponse = new CalculationResponse(
                    "An unexpected error occurred: " + e.getMessage(),
                    request.getFirstNumber(),
                    request.getSecondNumber(),
                    request.getOperator()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Add two numbers
     * GET /api/calculator/add?a=10&b=5
     */
    @GetMapping("/add")
    public ResponseEntity<CalculationResponse> add(@RequestParam Double a, @RequestParam Double b) {
        CalculationRequest request = new CalculationRequest(a, b, "+");
        CalculationResponse response = calculatorService.calculate(request);
        historyService.addToHistory(request, response);
        return ResponseEntity.ok(response);
    }

    /**
     * Subtract two numbers
     * GET /api/calculator/subtract?a=10&b=5
     */
    @GetMapping("/subtract")
    public ResponseEntity<CalculationResponse> subtract(@RequestParam Double a, @RequestParam Double b) {
        CalculationRequest request = new CalculationRequest(a, b, "-");
        CalculationResponse response = calculatorService.calculate(request);
        historyService.addToHistory(request, response);
        return ResponseEntity.ok(response);
    }

    /**
     * Multiply two numbers
     * GET /api/calculator/multiply?a=10&b=5
     */
    @GetMapping("/multiply")
    public ResponseEntity<CalculationResponse> multiply(@RequestParam Double a, @RequestParam Double b) {
        CalculationRequest request = new CalculationRequest(a, b, "*");
        CalculationResponse response = calculatorService.calculate(request);
        historyService.addToHistory(request, response);
        return ResponseEntity.ok(response);
    }

    /**
     * Divide two numbers
     * GET /api/calculator/divide?a=10&b=5
     */
    @GetMapping("/divide")
    public ResponseEntity<CalculationResponse> divide(@RequestParam Double a, @RequestParam Double b) {
        CalculationRequest request = new CalculationRequest(a, b, "/");
        CalculationResponse response = calculatorService.calculate(request);
        historyService.addToHistory(request, response);
        return ResponseEntity.ok(response);
    }

    /**
     * Get calculation history
     * GET /api/calculator/history
     */
    @GetMapping("/history")
    public ResponseEntity<Map<String, Object>> getHistory() {
        log.info("Retrieving calculation history");
        List<CalculationHistory> history = historyService.getHistory();

        Map<String, Object> response = new HashMap<>();
        response.put("history", history);
        response.put("count", history.size());

        return ResponseEntity.ok(response);
    }

    /**
     * Get last N calculations
     * GET /api/calculator/history/last?n=10
     */
    @GetMapping("/history/last")
    public ResponseEntity<Map<String, Object>> getLastCalculations(@RequestParam(defaultValue = "10") int n) {
        log.info("Retrieving last {} calculations", n);

        try {
            List<CalculationHistory> history = historyService.getLastNCalculations(n);

            Map<String, Object> response = new HashMap<>();
            response.put("requested", n);
            response.put("returned", history.size());
            response.put("history", history);

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    /**
     * Clear calculation history
     * DELETE /api/calculator/history
     */
    @DeleteMapping("/history")
    public ResponseEntity<Map<String, String>> clearHistory() {
        log.info("Clearing calculation history");
        historyService.clearHistory();

        Map<String, String> response = new HashMap<>();
        response.put("message", "Calculation history cleared successfully");
        return ResponseEntity.ok(response);
    }

    /**
     * Health check endpoint
     * GET /api/calculator/health
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "Calculator API");
        response.put("version", "1.0.0");
        return ResponseEntity.ok(response);
    }
}

