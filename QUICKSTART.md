# Quick Start Guide - Calculator Web Application

## ⚡ 5-Minute Setup

### Prerequisites
- Java 17+ (`java -version`)
- Maven 3.8+ (`mvn -version`)

### Step-by-Step

#### 1. Build the Application
```powershell
# Navigate to project directory
cd calculator-app

# Build with Maven
mvn clean install
```

Should see: `BUILD SUCCESS`

#### 2. Start the Application
```powershell
mvn spring-boot:run
```

Should see: `Started CalculatorApplication in ... seconds`

#### 3. Open in Browser
- Open: http://localhost:8080
- You should see the calculator interface

#### 4. Start Calculating!
- Click buttons to perform calculations
- View history in the right panel

---

## 🎯 Common Tasks

### Run Tests
```powershell
mvn test
```

### Run as JAR
```powershell
mvn clean package
java -jar target/calculator-app-1.0.0.jar
```

### Run with Docker
```powershell
docker build -t calculator-app:1.0 .
docker run -p 8080:8080 calculator-app:1.0
```

### Run with Docker Compose
```powershell
docker-compose up -d
docker-compose logs -f
```

---

## 🌐 API Examples

### Simple Addition
```powershell
Invoke-WebRequest -Uri "http://localhost:8080/api/calculator/add?a=10&b=5" -Method GET
```

### Using POST Endpoint
```powershell
$body = @{
    firstNumber = 10
    secondNumber = 5
    operator = "+"
} | ConvertTo-Json

Invoke-WebRequest -Uri "http://localhost:8080/api/calculator/calculate" `
  -Method POST `
  -Headers @{"Content-Type"="application/json"} `
  -Body $body
```

### Get History
```powershell
Invoke-WebRequest -Uri "http://localhost:8080/api/calculator/history" -Method GET
```

---

## 🐛 Troubleshooting

| Issue | Solution |
|-------|----------|
| Port 8080 in use | Change in `application.properties`: `server.port=9090` |
| Java not found | Install Java 17+ from adoptium.net |
| Maven not found | Install Maven from maven.apache.org |
| Tests fail | Run `mvn clean install` again |

---

## 📖 Full Documentation
See `README.md` for complete documentation.

---

**That's it! Enjoy your calculator! 🎉**

