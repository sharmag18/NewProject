@echo off
echo 🎯 Running Milestone 2: REST API for Products
echo ==============================================
echo.

echo 📋 Step 1: Compiling and running the Spring Boot application...
echo.
echo 🚀 Starting the REST API server...
echo 📁 Make sure MySQL is running on localhost:3306
echo 📁 Make sure the database 'ecommerce' exists with data
echo.

call mvn spring-boot:run

echo.
echo ✅ Milestone 2 execution completed!
pause 