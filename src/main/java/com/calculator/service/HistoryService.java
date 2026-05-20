package com.calculator.service;

import com.calculator.model.CalculationHistory;
import com.calculator.model.CalculationRequest;
import com.calculator.model.CalculationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * Service layer for managing calculation history
 */
@Slf4j
@Service
public class HistoryService {

    private static final int MAX_HISTORY_SIZE = 100;
    private final List<CalculationHistory> history = new ArrayList<>();

    /**
     * Add a calculation to history
     */
    public void addToHistory(CalculationRequest request, CalculationResponse response) {
        CalculationHistory entry = new CalculationHistory(request, response);

        // Keep history size manageable
        if (history.size() >= MAX_HISTORY_SIZE) {
            history.remove(0); // Remove oldest entry
        }

        history.add(entry);
        log.debug("History entry added. Current history size: {}", history.size());
    }

    /**
     * Get all calculation history
     */
    public List<CalculationHistory> getHistory() {
        log.debug("Retrieving calculation history. Size: {}", history.size());
        return new ArrayList<>(history); // Return copy to prevent external modification
    }

    /**
     * Get last N calculations
     */
    public List<CalculationHistory> getLastNCalculations(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Number of calculations must be positive");
        }

        int startIndex = Math.max(0, history.size() - n);
        log.debug("Retrieving last {} calculations", n);
        return new ArrayList<>(history.subList(startIndex, history.size()));
    }

    /**
     * Clear all history
     */
    public void clearHistory() {
        log.info("Clearing calculation history");
        history.clear();
    }

    /**
     * Get history size
     */
    public int getHistorySize() {
        return history.size();
    }
}

