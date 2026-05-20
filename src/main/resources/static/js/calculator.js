/**
 * Calculator JavaScript - Frontend Logic
 * Handles all client-side calculator operations and API communication
 */

// API Base URL
const API_BASE_URL = '/api/calculator';

// State Management
let calculatorState = {
    display: '0',
    previousValue: null,
    operator: null,
    waitingForNewValue: false,
    history: []
};

/**
 * Initialize the calculator
 */
document.addEventListener('DOMContentLoaded', () => {
    console.log('Calculator initialized');
    updateDisplay();
    loadHistory();
    // Keyboard support
    document.addEventListener('keydown', handleKeyPress);
});

/**
 * Append number to display
 */
function appendNumber(num) {
    console.log('Appended number:', num);

    if (calculatorState.waitingForNewValue) {
        calculatorState.display = num;
        calculatorState.waitingForNewValue = false;
    } else {
        if (calculatorState.display === '0') {
            calculatorState.display = num;
        } else {
            calculatorState.display += num;
        }
    }

    updateDisplay();
}

/**
 * Append decimal point
 */
function appendDecimal(decimal) {
    console.log('Appended decimal');

    if (calculatorState.waitingForNewValue) {
        calculatorState.display = '0' + decimal;
        calculatorState.waitingForNewValue = false;
    } else {
        if (calculatorState.display.indexOf(decimal) === -1) {
            calculatorState.display += decimal;
        }
    }

    updateDisplay();
}

/**
 * Set operator
 */
function setOperator(op) {
    console.log('Set operator:', op);

    if (calculatorState.operator !== null && !calculatorState.waitingForNewValue) {
        // If there's a pending operation, calculate it first
        calculatePending();
    }

    calculatorState.previousValue = calculatorState.display;
    calculatorState.operator = op;
    calculatorState.waitingForNewValue = true;

    updateOperationInfo();
}

/**
 * Calculate the result
 */
function calculate() {
    console.log('Calculate called');

    if (calculatorState.operator === null || calculatorState.previousValue === null) {
        showStatus('Please select an operator', 'error');
        return;
    }

    calculatePending();
}

/**
 * Calculate pending operation
 */
function calculatePending() {
    if (calculatorState.operator === null) {
        return;
    }

    const firstNumber = parseNumber(calculatorState.previousValue);
    const secondNumber = parseNumber(calculatorState.display);
    const operator = calculatorState.operator;

    console.log(`Calculating: ${firstNumber} ${operator} ${secondNumber}`);

    // Make API call
    const request = {
        firstNumber: firstNumber,
        secondNumber: secondNumber,
        operator: operator
    };

    fetch(`${API_BASE_URL}/calculate`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(request)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return response.json();
    })
    .then(data => {
        console.log('API Response:', data);

        if (data.success) {
            calculatorState.display = formatResult(data.result);
            showStatus(`${data.firstNumber} ${data.operation} ${data.secondNumber} = ${data.result}`, 'success');
        } else {
            showStatus(data.errorMessage, 'error');
            calculatorState.display = '0';
        }

        calculatorState.previousValue = null;
        calculatorState.operator = null;
        calculatorState.waitingForNewValue = true;

        updateDisplay();
        updateOperationInfo();
        loadHistory();
    })
    .catch(error => {
        console.error('Error during calculation:', error);
        showStatus('Error: ' + error.message, 'error');
        calculatorState.display = '0';
        calculatorState.previousValue = null;
        calculatorState.operator = null;
        calculatorState.waitingForNewValue = false;
        updateDisplay();
    });
}

/**
 * Clear display
 */
function clearDisplay() {
    console.log('Clear display');
    calculatorState.display = '0';
    calculatorState.previousValue = null;
    calculatorState.operator = null;
    calculatorState.waitingForNewValue = false;
    updateDisplay();
    updateOperationInfo();
    showStatus('', '');
}

/**
 * Delete last character
 */
function deleteLast() {
    console.log('Delete last character');

    if (calculatorState.display.length === 1) {
        calculatorState.display = '0';
    } else {
        calculatorState.display = calculatorState.display.slice(0, -1);
    }

    updateDisplay();
}

/**
 * Toggle sign (positive/negative)
 */
function toggleSign() {
    console.log('Toggle sign');

    const num = parseNumber(calculatorState.display);
    calculatorState.display = (-num).toString();
    updateDisplay();
}

/**
 * Update display
 */
function updateDisplay() {
    const displayElement = document.getElementById('display');
    if (displayElement) {
        displayElement.value = calculatorState.display;
    }
}

/**
 * Update operation info
 */
function updateOperationInfo() {
    const operationInfoElement = document.getElementById('operationInfo');
    if (operationInfoElement) {
        if (calculatorState.operator) {
            operationInfoElement.textContent = `${calculatorState.previousValue} ${calculatorState.operator}`;
        } else {
            operationInfoElement.textContent = '';
        }
    }
}

/**
 * Load and display history
 */
function loadHistory() {
    fetch(`${API_BASE_URL}/history`)
    .then(response => response.json())
    .then(data => {
        console.log('History loaded:', data);
        displayHistory(data.history);
    })
    .catch(error => {
        console.error('Error loading history:', error);
    });
}

/**
 * Display history items
 */
function displayHistory(historyArray) {
    const historyList = document.getElementById('historyList');

    if (!historyList) return;

    if (!historyArray || historyArray.length === 0) {
        historyList.innerHTML = '<p class="empty-message">No calculations yet</p>';
        return;
    }

    // Display last 10 items
    const itemsToShow = historyArray.slice(-10).reverse();

    historyList.innerHTML = itemsToShow.map(item => {
        const isError = !item.success;
        const operationSymbol = getOperationSymbol(item.operator);

        let html = `<div class="history-item ${isError ? 'error' : ''}">`;
        html += `<div class="history-operation">${item.firstNumber} ${operationSymbol} ${item.secondNumber}</div>`;

        if (isError) {
            html += `<div class="history-error">Error: ${item.errorMessage}</div>`;
        } else {
            html += `<div class="history-result">= ${formatResult(item.result)}</div>`;
        }

        html += `<div class="history-time">${formatTime(item.timestamp)}</div>`;
        html += `</div>`;

        return html;
    }).join('');
}

/**
 * Clear history
 */
function clearHistory() {
    if (confirm('Are you sure you want to clear the calculation history?')) {
        fetch(`${API_BASE_URL}/history`, {
            method: 'DELETE'
        })
        .then(response => response.json())
        .then(data => {
            console.log('History cleared:', data);
            loadHistory();
            showStatus(data.message, 'success');
        })
        .catch(error => {
            console.error('Error clearing history:', error);
            showStatus('Error clearing history', 'error');
        });
    }
}

/**
 * Show status message
 */
function showStatus(message, type) {
    const statusElement = document.getElementById('status');
    if (statusElement) {
        statusElement.textContent = message;
        statusElement.className = 'status ' + (type ? type : '');
    }
}

/**
 * Parse number from string
 */
function parseNumber(str) {
    return parseFloat(str) || 0;
}

/**
 * Format result (remove trailing zeros)
 */
function formatResult(num) {
    if (typeof num !== 'number') {
        return num;
    }

    // Check if result is a whole number
    if (Number.isInteger(num)) {
        return num.toString();
    }

    // Limit decimal places to avoid floating point errors
    return parseFloat(num.toFixed(10)).toString();
}

/**
 * Get operation symbol
 */
function getOperationSymbol(operator) {
    const symbolMap = {
        '+': '+',
        '-': '-',
        '*': '×',
        '/': '÷'
    };
    return symbolMap[operator] || operator;
}

/**
 * Format timestamp
 */
function formatTime(timestamp) {
    if (!timestamp) return '';

    try {
        const date = new Date(timestamp);
        const now = new Date();
        const diffMs = now - date;
        const diffMins = Math.floor(diffMs / 60000);
        const diffSecs = Math.floor(diffMs / 1000);

        if (diffSecs < 60) {
            return 'Just now';
        } else if (diffMins < 60) {
            return `${diffMins}m ago`;
        } else {
            return date.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
        }
    } catch (e) {
        return '';
    }
}

/**
 * Keyboard support
 */
function handleKeyPress(event) {
    const key = event.key;

    // Number keys
    if (/^[0-9]$/.test(key)) {
        appendNumber(key);
    }

    // Decimal point
    if (key === '.') {
        appendDecimal('.');
    }

    // Operators
    switch (key) {
        case '+':
            event.preventDefault();
            setOperator('+');
            break;
        case '-':
            event.preventDefault();
            setOperator('-');
            break;
        case '*':
            event.preventDefault();
            setOperator('*');
            break;
        case '/':
            event.preventDefault();
            setOperator('/');
            break;
        case 'Enter':
        case '=':
            event.preventDefault();
            calculate();
            break;
        case 'Backspace':
            event.preventDefault();
            deleteLast();
            break;
        case 'Escape':
        case 'c':
        case 'C':
            event.preventDefault();
            clearDisplay();
            break;
    }
}

// Log to console that calculator is ready
console.log('Calculator Web Application Ready');
console.log('API Base URL:', API_BASE_URL);

