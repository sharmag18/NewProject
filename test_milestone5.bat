@echo off
echo ========================================
echo Testing E-commerce API - Milestone 5
echo Advanced Features & Performance Tests
echo ========================================

echo.
echo Waiting for application to start...
timeout /t 10 /nobreak >nul

echo.
echo ========================================
echo 1. Testing Basic Product Endpoints
echo ========================================

echo.
echo Testing GET /api/products (with pagination and sorting)...
curl -X GET "http://localhost:8081/api/products?page=1&size=5&sortBy=name&sortDirection=asc" -H "Content-Type: application/json"

echo.
echo.
echo Testing GET /api/products/1...
curl -X GET "http://localhost:8081/api/products/1" -H "Content-Type: application/json"

echo.
echo ========================================
echo 2. Testing Advanced Search Features
echo ========================================

echo.
echo Testing POST /api/products/search (advanced search)...
curl -X POST "http://localhost:8081/api/products/search" -H "Content-Type: application/json" -d "{\"name\":\"iPhone\",\"minPrice\":500,\"maxPrice\":1000,\"sortBy\":\"retailPrice\",\"sortDirection\":\"desc\",\"page\":1,\"size\":5}"

echo.
echo.
echo Testing GET /api/products/search?q=phone...
curl -X GET "http://localhost:8081/api/products/search?q=phone&page=1&size=5" -H "Content-Type: application/json"

echo.
echo.
echo Testing GET /api/products/price-range?minPrice=100&maxPrice=500...
curl -X GET "http://localhost:8081/api/products/price-range?minPrice=100&maxPrice=500" -H "Content-Type: application/json"

echo.
echo ========================================
echo 3. Testing Filtering Endpoints
echo ========================================

echo.
echo Testing GET /api/products/department/Electronics...
curl -X GET "http://localhost:8081/api/products/department/Electronics?page=1&size=5" -H "Content-Type: application/json"

echo.
echo.
echo Testing GET /api/products/brand/Apple...
curl -X GET "http://localhost:8081/api/products/brand/Apple?page=1&size=5" -H "Content-Type: application/json"

echo.
echo.
echo Testing GET /api/products/category/Smartphones...
curl -X GET "http://localhost:8081/api/products/category/Smartphones?page=1&size=5" -H "Content-Type: application/json"

echo.
echo ========================================
echo 4. Testing Statistics Endpoints
echo ========================================

echo.
echo Testing GET /api/products/stats...
curl -X GET "http://localhost:8081/api/products/stats" -H "Content-Type: application/json"

echo.
echo.
echo Testing GET /api/products/stats/departments...
curl -X GET "http://localhost:8081/api/products/stats/departments" -H "Content-Type: application/json"

echo.
echo.
echo Testing GET /api/products/stats/brands...
curl -X GET "http://localhost:8081/api/products/stats/brands" -H "Content-Type: application/json"

echo.
echo.
echo Testing GET /api/products/stats/categories...
curl -X GET "http://localhost:8081/api/products/stats/categories" -H "Content-Type: application/json"

echo.
echo ========================================
echo 5. Testing CRUD Operations
echo ========================================

echo.
echo Testing POST /api/products (create product)...
curl -X POST "http://localhost:8081/api/products" -H "Content-Type: application/json" -d "{\"name\":\"Test Product\",\"brand\":\"Test Brand\",\"category\":\"Test Category\",\"cost\":50.00,\"retailPrice\":99.99,\"sku\":\"TEST001\",\"distributionCenterId\":1}"

echo.
echo.
echo Testing PUT /api/products/1 (update product)...
curl -X PUT "http://localhost:8081/api/products/1" -H "Content-Type: application/json" -d "{\"name\":\"Updated iPhone\",\"brand\":\"Apple\",\"category\":\"Smartphones\",\"cost\":199.99,\"retailPrice\":999.99,\"sku\":\"IPH14PRO001\",\"distributionCenterId\":1}"

echo.
echo ========================================
echo 6. Testing Validation & Error Handling
echo ========================================

echo.
echo Testing validation error (invalid page number)...
curl -X GET "http://localhost:8081/api/products?page=0" -H "Content-Type: application/json"

echo.
echo.
echo Testing validation error (invalid sort field)...
curl -X GET "http://localhost:8081/api/products?sortBy=invalid" -H "Content-Type: application/json"

echo.
echo.
echo Testing resource not found...
curl -X GET "http://localhost:8081/api/products/999999" -H "Content-Type: application/json"

echo.
echo ========================================
echo 7. Testing Cache Management
echo ========================================

echo.
echo Testing cache clear endpoint...
curl -X POST "http://localhost:8081/api/products/cache/clear" -H "Content-Type: application/json"

echo.
echo ========================================
echo 8. Testing Health & Monitoring
echo ========================================

echo.
echo Testing health endpoint...
curl -X GET "http://localhost:8081/api/products/health" -H "Content-Type: application/json"

echo.
echo.
echo Testing actuator endpoints...
curl -X GET "http://localhost:8081/api/actuator/health" -H "Content-Type: application/json"

echo.
echo.
echo Testing metrics endpoint...
curl -X GET "http://localhost:8081/api/actuator/metrics" -H "Content-Type: application/json"

echo.
echo ========================================
echo 9. Testing Rate Limiting
echo ========================================

echo.
echo Testing rate limiting (making multiple rapid requests)...
for /L %%i in (1,1,10) do (
    curl -X GET "http://localhost:8081/api/products?page=1&size=1" -H "Content-Type: application/json" -s >nul
    echo Request %%i completed
)

echo.
echo ========================================
echo Testing Complete!
echo ========================================

echo.
echo All tests completed. Check the output above for results.
echo.
echo Key Features Tested:
echo - Advanced search with multiple criteria
echo - Pagination and sorting
echo - Caching (Redis)
echo - Rate limiting
echo - Input validation
echo - Error handling
echo - Performance monitoring
echo - Statistics endpoints
echo - CRUD operations
echo.
pause 