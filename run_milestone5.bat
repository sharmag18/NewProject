@echo off
echo ========================================
echo Starting E-commerce API - Milestone 5
echo Advanced Features ^& Performance Optimization
echo ========================================

echo.
echo Checking if Redis is running...
redis-cli ping >nul 2>&1
if %errorlevel% neq 0 (
    echo WARNING: Redis is not running. Starting Redis...
    echo Please install Redis or start it manually.
    echo You can download Redis from: https://redis.io/download
    echo.
    echo For Windows, you can use:
    echo 1. Redis for Windows: https://github.com/microsoftarchive/redis/releases
    echo 2. Or use Docker: docker run -d -p 6379:6379 redis:alpine
    echo.
    pause
)

echo.
echo Building and starting the application...
echo.

mvn clean compile
if %errorlevel% neq 0 (
    echo Build failed! Please check the errors above.
    pause
    exit /b 1
)

echo.
echo Starting Spring Boot application...
echo.

mvn spring-boot:run

echo.
echo Application stopped.
pause 