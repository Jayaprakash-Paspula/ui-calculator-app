# Calculator Web Application

A simple yet powerful calculator web application built with Spring Boot, featuring a clean UI, REST API, calculation history, and comprehensive error handling.

## 📋 Table of Contents

- [Features](#features)
- [Technology Stack](#technology-stack)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Installation & Setup](#installation--setup)
- [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
- [Usage Guide](#usage-guide)
- [Testing](#testing)
- [Docker Deployment](#docker-deployment)
- [Troubleshooting](#troubleshooting)
- [Future Enhancements](#future-enhancements)

## ✨ Features

### Backend (Spring Boot REST API)
- ✅ Basic arithmetic operations: Addition, Subtraction, Multiplication, Division
- ✅ Proper division by zero error handling
- ✅ RESTful API with JSON responses
- ✅ Calculation history tracking (stores up to 100 entries)
- ✅ Comprehensive input validation
- ✅ Logging with SLF4J
- ✅ Health check endpoint
- ✅ CORS support

### Frontend (HTML/CSS/JavaScript)
- ✅ Clean, modern calculator UI (mobile-optimized)
- ✅ Real-time calculation display
- ✅ Keyboard support (number keys, operators, Enter, Backspace, Escape)
- ✅ Responsive design (works on desktop, tablet, mobile)
- ✅ Calculation history panel with timestamps
- ✅ Error display and handling
- ✅ Clear history functionality
- ✅ Operation tracking display

### Quality & Reliability
- ✅ Comprehensive unit tests (JUnit 5)
- ✅ Service layer architecture
- ✅ Production-ready error handling
- ✅ Docker containerization
- ✅ Docker Compose for easy deployment
- ✅ Health checks

## 🔧 Technology Stack

- **Java**: 17+
- **Spring Boot**: 3.2.0
- **Build Tool**: Maven 3.9+
- **Frontend**: HTML5, CSS3, Vanilla JavaScript
- **Testing**: JUnit 5, Mockito
- **Logging**: SLF4J with Logback
- **Containerization**: Docker & Docker Compose
- **IDE**: Works with any Java IDE (IntelliJ, VS Code, Eclipse, etc.)

## 📁 Project Structure

```
calculator-app/
├── src/
│   ├── main/
│   │   ├── java/com/calculator/
│   │   │   ├── CalculatorApplication.java       (Main class)
│   │   │   ├── controller/
│   │   │   │   └── CalculatorController.java    (REST endpoints)
│   │   │   ├── service/
│   │   │   │   ├── CalculatorService.java       (Business logic)
│   │   │   │   └── HistoryService.java          (History management)
│   │   │   └── model/
│   │   │       ├── CalculationRequest.java
│   │   │       ├── CalculationResponse.java
│   │   │       └── CalculationHistory.java
│   │   └── resources/
│   │       ├── application.properties           (Configuration)
│   │       └── static/
│   │           ├── index.html                   (UI)
│   │           ├── css/styles.css               (Styling)
│   │           └── js/calculator.js             (Frontend logic)
│   └── test/
│       └── java/com/calculator/service/
│           ├── CalculatorServiceTest.java
│           └── HistoryServiceTest.java
├── pom.xml                                      (Maven configuration)
├── Dockerfile                                   (Docker image)
├── docker-compose.yml                           (Docker Compose)
└── README.md                                    (This file)
```

## 📦 Prerequisites

Before running the application, ensure you have the following installed:

### Minimum Requirements
- **Java Development Kit (JDK)**: Version 17 or higher
  - Download from: https://adoptium.net or https://www.oracle.com/java/technologies/downloads/

- **Maven**: Version 3.8 or higher
  - Download from: https://maven.apache.org/download.cgi
  - Or install via package manager:
    - **Windows (Chocolatey)**: `choco install maven`
    - **macOS (Homebrew)**: `brew install maven`
    - **Linux (apt)**: `sudo apt-get install maven`

### Optional (For Docker deployment)
- **Docker**: Version 20.10 or higher
  - Download from: https://www.docker.com/products/docker-desktop
- **Docker Compose**: Version 2.0 or higher (included with Docker Desktop)

### Verification
Verify installations:
```powershell
java -version
mvn -version
docker --version      # if using Docker
docker-compose --version  # if using Docker
```

## 🚀 Installation & Setup

### Step 1: Clone/Download the Project
```powershell
cd calculator-app
```

### Step 2: Build the Project
```powershell
# Clean and build
mvn clean install

# Check the build output
# Should see: BUILD SUCCESS
```

### Step 3: Verify Build Artifacts
```powershell
dir target/
# Should contain calculator-app-1.0.0.jar
```

## ▶️ Running the Application

### Option 1: Run with Maven (Recommended for Development)
```powershell
mvn spring-boot:run
```

Expected output:
```
2024-... : The following 1 profile is active: "default"
...
2024-... : Started CalculatorApplication in ... seconds
2024-... : Tomcat started on port(s): 8080 (http)
```

### Option 2: Run Spring Boot JAR Directly
```powershell
# First build the application
mvn clean package

# Then run the JAR
java -jar target/calculator-app-1.0.0.jar
```

### Option 3: Run with Docker
```powershell
# Build Docker image
docker build -t calculator-app:1.0 .

# Run Docker container
docker run -p 8080:8080 calculator-app:1.0
```

### Option 4: Run with Docker Compose
```powershell
# Start services
docker-compose up

# In another terminal, check logs
docker-compose logs -f

# Stop services
docker-compose down
```

## 🌐 Accessing the Application

Once the application is running:

### Frontend UI
- **URL**: http://localhost:8080
- Open in your web browser
- Use the calculator interface to perform calculations

### API Endpoints
All API endpoints are accessible at: `http://localhost:8080/api/calculator`

## 📡 API Endpoints

### Calculation Endpoints

#### 1. Calculate (Generic POST)
```http
POST /api/calculator/calculate
Content-Type: application/json

{
  "firstNumber": 10,
  "secondNumber": 5,
  "operator": "+"
}
```

**Response (Success):**
```json
{
  "result": 15,
  "operation": "+",
  "firstNumber": 10,
  "secondNumber": 5,
  "success": true,
  "errorMessage": null,
  "timestamp": "2024-05-04T10:30:45.123"
}
```

**Response (Error):**
```json
{
  "result": null,
  "operation": "/",
  "firstNumber": 10,
  "secondNumber": 0,
  "success": false,
  "errorMessage": "Cannot divide by zero",
  "timestamp": "2024-05-04T10:30:45.123"
}
```

#### 2. Quick Operations (GET)
```http
# Addition
GET /api/calculator/add?a=10&b=5

# Subtraction
GET /api/calculator/subtract?a=10&b=5

# Multiplication
GET /api/calculator/multiply?a=10&b=5

# Division
GET /api/calculator/divide?a=10&b=5
```

### History Endpoints

#### 3. Get All History
```http
GET /api/calculator/history
```

**Response:**
```json
{
  "history": [
    {
      "firstNumber": 10,
      "secondNumber": 5,
      "operator": "+",
      "result": 15,
      "success": true,
      "errorMessage": null,
      "timestamp": "2024-05-04T10:30:45.123"
    }
  ],
  "count": 1
}
```

#### 4. Get Last N Calculations
```http
GET /api/calculator/history/last?n=10
```

**Response:**
```json
{
  "requested": 10,
  "returned": 5,
  "history": [...]
}
```

#### 5. Clear History
```http
DELETE /api/calculator/history
```

**Response:**
```json
{
  "message": "Calculation history cleared successfully"
}
```

### Utility Endpoints

#### 6. Health Check
```http
GET /api/calculator/health
```

**Response:**
```json
{
  "status": "UP",
  "service": "Calculator API",
  "version": "1.0.0"
}
```

## 💡 Usage Guide

### Using the Frontend UI

1. **Open the Calculator**
   - Navigate to http://localhost:8080 in your browser

2. **Perform Calculations**
   - Click number buttons to enter digits (0-9)
   - Click decimal point (.) to add decimals
   - Click operator buttons (+, -, ×, ÷)
   - Click = to calculate result
   - Result will be displayed in the display area

3. **Additional Functions**
   - **C (Clear)**: Clears the display and resets state
   - **DEL**: Deletes the last entered digit
   - **+/-**: Toggles positive/negative sign

4. **View History**
   - Calculation history appears in right panel
   - Shows operation, result, and timestamp
   - Click "Clear" to delete all history

5. **Keyboard Shortcuts**
   - **Number keys** (0-9): Enter digits
   - **Decimal** (.): Add decimal point
   - **Operators** (+, -, *, /): Set operators
   - **Enter or =**: Calculate result
   - **Backspace**: Delete last digit
   - **Escape or C**: Clear display

### Using the API Directly

#### Example: Using cURL or REST client

```powershell
# Addition
Invoke-WebRequest -Uri "http://localhost:8080/api/calculator/calculate" `
  -Method POST `
  -Headers @{"Content-Type"="application/json"} `
  -Body '{"firstNumber":10,"secondNumber":5,"operator":"+"}'

# Get history
Invoke-WebRequest -Uri "http://localhost:8080/api/calculator/history" -Method GET

# Health check
Invoke-WebRequest -Uri "http://localhost:8080/api/calculator/health" -Method GET
```

## 🧪 Testing

### Run All Tests
```powershell
mvn test
```

### Run Specific Test Class
```powershell
mvn test -Dtest=CalculatorServiceTest
mvn test -Dtest=HistoryServiceTest
```

### Run Tests with Coverage Report
```powershell
mvn clean test jacoco:report
# Report available at: target/site/jacoco/index.html
```

### Test Coverage
The project includes:
- **CalculatorServiceTest**: Tests for all arithmetic operations
  - Addition, Subtraction, Multiplication, Division
  - Edge cases (negative numbers, division by zero)
  - Input validation
  - Error handling

- **HistoryServiceTest**: Tests for history management
  - Adding to history
  - Retrieving history
  - Clearing history
  - Edge cases

## 🐳 Docker Deployment

### Build Docker Image
```powershell
docker build -t calculator-app:1.0 .
```

### Run Docker Container
```powershell
docker run -p 8080:8080 --name calculator calculator-app:1.0
```

### Verify Container is Running
```powershell
docker ps
docker logs calculator
```

### Check Health
```powershell
docker exec calculator curl http://localhost:8080/api/calculator/health
```

### Stop Container
```powershell
docker stop calculator
docker rm calculator
```

### Docker Compose (Production Setup)
```powershell
# Start
docker-compose up -d

# Check logs
docker-compose logs -f calculator-app

# Stop
docker-compose down
```

## 📋 Logs

### View Logs (Development)
When running with Maven:
```
Logs are displayed in the console
```

### View Logs (Docker)
```powershell
docker logs calculator
docker logs -f calculator  # Follow logs
```

### Log Files
- Location: `logs/calculator.log` (if volume mounted in Docker)
- Format: `YYYY-MM-DD HH:mm:ss [thread] LEVEL logger - message`

## 🔧 Configuration

Edit `src/main/resources/application.properties` to customize:

```properties
# Server Port
server.port=8080

# Logging Level
logging.level.root=INFO
logging.level.com.calculator=DEBUG

# Application Name
spring.application.name=calculator-app
```

## 🐛 Troubleshooting

### Port 8080 Already in Use
```powershell
# Find process using port 8080
Get-NetTcpConnection -LocalPort 8080

# Kill process (Windows)
taskkill /PID <PID> /F

# Or change port in application.properties
# server.port=9090
```

### Maven Build Fails
```powershell
# Clean cache
mvn clean

# Update dependencies
mvn clean install -U

# Check Java version (must be 17+)
java -version
```

### Application Won't Start
1. Check if port 8080 is available
2. Verify Java 17+ is installed
3. Check logs for errors
4. Ensure all dependencies are downloaded

### Docker Issues
```powershell
# Remove dangling images
docker image prune

# Rebuild without cache
docker build --no-cache -t calculator-app:1.0 .

# Check Docker logs
docker logs <container_id>
```

## 📈 Performance & Optimization

- **Stateless API**: All requests are independent
- **History Limit**: Limited to 100 entries to manage memory
- **Efficient Calculations**: Direct method calls for arithmetic
- **Responsive UI**: CSS animations and smooth transitions
- **Error Recovery**: Graceful error handling prevents crashes

## 🚀 Future Enhancements

- [ ] Advanced operations (exponentiation, square root, trigonometry)
- [ ] Expression parser (support for complex expressions)
- [ ] Calculation export (CSV/PDF)
- [ ] User authentication and saved histories
- [ ] API documentation (Swagger/OpenAPI)
- [ ] Database persistence (PostgreSQL)
- [ ] WebSocket support for real-time updates
- [ ] Mobile app (React Native)
- [ ] Internationalization (i18n)
- [ ] Dark mode theme

## 📝 License

This project is open source and available for personal and educational use.

## 🤝 Support

For issues or questions:
1. Check the Troubleshooting section
2. Review application logs
3. Check API responses for error messages
4. Verify all prerequisites are installed

## 👨‍💻 Development Notes

### Adding New Operations
1. Update `CalculatorService.java` with new method
2. Add new switch case in `performOperation()`
3. Update controller endpoints
4. Add unit tests
5. Update frontend buttons

### Extending History
1. Modify `HistoryService.java` max size
2. Add new query methods as needed
3. Update API endpoints
4. Update frontend history display

### Deployment Checklist
- [ ] All tests passing
- [ ] No compiler warnings
- [ ] Updated application.properties
- [ ] Docker image builds successfully
- [ ] Container runs and is healthy
- [ ] All endpoints respond correctly

## 📞 Quick Reference

| Task | Command |
|------|---------|
| Build | `mvn clean install` |
| Run (Maven) | `mvn spring-boot:run` |
| Run (JAR) | `java -jar target/calculator-app-1.0.0.jar` |
| Test | `mvn test` |
| Docker Build | `docker build -t calculator-app:1.0 .` |
| Docker Run | `docker run -p 8080:8080 calculator-app:1.0` |
| Compose Up | `docker-compose up -d` |
| Compose Down | `docker-compose down` |
| API Health | `curl http://localhost:8080/api/calculator/health` |
| Frontend | http://localhost:8080 |

---

**Version**: 1.0.0  
**Last Updated**: May 4, 2024  
**Status**: Production Ready ✅

