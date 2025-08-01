@echo off
echo 🎯 Running Milestone 4: Database Refactoring
echo ============================================
echo.

echo 📋 Step 1: Building the project...
call mvn clean compile
if %errorlevel% neq 0 (
    echo ❌ Build failed!
    pause
    exit /b 1
)

echo.
echo 📋 Step 2: Starting Spring Boot application...
echo 🔄 This will automatically:
echo    - Create the departments table
echo    - Update the products table structure
echo    - Migrate existing data to use foreign keys
echo    - Load sample data with proper relationships
echo.

call mvn spring-boot:run

echo.
echo ✅ Milestone 4 execution completed!
pause 