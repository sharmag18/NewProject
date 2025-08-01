@echo off
echo 🎯 Running Milestone 3: Frontend UI for Products
echo ================================================
echo.

echo 📋 Step 1: Checking if backend API is running...
echo 📁 Make sure the Spring Boot API is running on http://localhost:8081
echo.

echo 📋 Step 2: Installing frontend dependencies...
cd frontend
call npm install
if %errorlevel% neq 0 (
    echo ❌ Failed to install dependencies!
    pause
    exit /b 1
)

echo.
echo 📋 Step 3: Starting the React development server...
echo 🌐 Frontend will be available at: http://localhost:3000
echo.

call npm start

echo.
echo ✅ Milestone 3 execution completed!
pause 