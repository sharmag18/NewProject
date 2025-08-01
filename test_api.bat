@echo off
echo 🧪 Testing REST API Endpoints
echo =============================
echo.

echo 📋 Testing API endpoints...
echo.

echo 🔍 Test 1: Get all products (first page)
curl -s "http://localhost:8080/api/products?page=0&size=5" | python -m json.tool
echo.

echo 🔍 Test 2: Get product by ID (ID: 1)
curl -s "http://localhost:8080/api/products/1" | python -m json.tool
echo.

echo 🔍 Test 3: Get products by department (Electronics)
curl -s "http://localhost:8080/api/products/department/Electronics" | python -m json.tool
echo.

echo 🔍 Test 4: Get products by brand (Apple)
curl -s "http://localhost:8080/api/products/brand/Apple" | python -m json.tool
echo.

echo 🔍 Test 5: Search products (keyword: iPhone)
curl -s "http://localhost:8080/api/products/search?q=iPhone" | python -m json.tool
echo.

echo 🔍 Test 6: Get all departments
curl -s "http://localhost:8080/api/products/departments" | python -m json.tool
echo.

echo 🔍 Test 7: Get all brands
curl -s "http://localhost:8080/api/products/brands" | python -m json.tool
echo.

echo 🔍 Test 8: Get product statistics
curl -s "http://localhost:8080/api/products/stats" | python -m json.tool
echo.

echo 🔍 Test 9: Test error handling (non-existent product)
curl -s "http://localhost:8080/api/products/999" | python -m json.tool
echo.

echo ✅ API testing completed!
pause 