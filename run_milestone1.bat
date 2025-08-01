@echo off
echo 🎯 Running Milestone 1: Database Design and Loading Data
echo ========================================================
echo.

echo 📋 Step 1: Compiling the project...
call mvn clean compile
if %errorlevel% neq 0 (
    echo ❌ Compilation failed!
    pause
    exit /b 1
)

echo.
echo 📋 Step 2: Running the application...
echo Using sample CSV file: sample_products.csv
echo.

java -cp target/classes com.ecommerce.Milestone1App sample_products.csv

echo.
echo ✅ Milestone 1 execution completed!
pause 