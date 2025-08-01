@echo off
echo 🧪 Testing Milestone 4: Database Refactoring
echo ============================================
echo.

echo 📋 Testing Department API Endpoints...
echo.

echo 🔍 Test 1: Get all departments
curl -s "http://localhost:8081/api/departments" | echo.
echo.

echo 🔍 Test 2: Get department by ID (1)
curl -s "http://localhost:8081/api/departments/1" | echo.
echo.

echo 🔍 Test 3: Get department by name (Electronics)
curl -s "http://localhost:8081/api/departments/name/Electronics" | echo.
echo.

echo 🔍 Test 4: Get department statistics
curl -s "http://localhost:8081/api/departments/stats" | echo.
echo.

echo 📋 Testing Updated Product API Endpoints...
echo.

echo 🔍 Test 5: Get all products (should include department info)
curl -s "http://localhost:8081/api/products" | echo.
echo.

echo 🔍 Test 6: Get product by ID (1) - should show department relationship
curl -s "http://localhost:8081/api/products/1" | echo.
echo.

echo 🔍 Test 7: Get products by department (Electronics)
curl -s "http://localhost:8081/api/products/department/Electronics" | echo.
echo.

echo 🔍 Test 8: Get product statistics (should include department info)
curl -s "http://localhost:8081/api/products/stats" | echo.
echo.

echo ✅ Milestone 4 testing completed!
echo.
echo 📊 Expected Results:
echo    - Departments table created with 3 departments
echo    - Products table updated with department_id foreign key
echo    - All products have proper department relationships
echo    - API responses include department information
echo.
pause 