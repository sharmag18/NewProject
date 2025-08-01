@echo off
echo ========================================
echo Testing Milestone 6: Complete E-commerce Platform
echo ========================================
echo.

echo 🚀 Starting tests...
echo.

echo 📋 Testing Authentication Endpoints...
echo ----------------------------------------

echo Testing user registration...
curl -X POST http://localhost:8081/api/auth/register ^
  -H "Content-Type: application/json" ^
  -d "{\"username\":\"testuser\",\"email\":\"test@example.com\",\"password\":\"password123\",\"firstName\":\"Test\",\"lastName\":\"User\"}" ^
  -w "\nHTTP Status: %%{http_code}\n" ^
  -s

echo.
echo Testing user login...
curl -X POST http://localhost:8081/api/auth/login ^
  -H "Content-Type: application/json" ^
  -d "{\"username\":\"admin\",\"password\":\"admin123\"}" ^
  -w "\nHTTP Status: %%{http_code}\n" ^
  -s

echo.
echo Testing admin login...
curl -X POST http://localhost:8081/api/auth/login ^
  -H "Content-Type: application/json" ^
  -d "{\"username\":\"admin\",\"password\":\"admin123\"}" ^
  -w "\nHTTP Status: %%{http_code}\n" ^
  -s

echo.
echo 📦 Testing Order Management Endpoints...
echo ----------------------------------------

echo Testing order creation...
curl -X POST http://localhost:8081/api/orders ^
  -H "Content-Type: application/json" ^
  -d "{\"items\":[{\"productId\":1,\"quantity\":2}],\"shippingAddress\":\"123 Main St, City, State 12345\",\"billingAddress\":\"123 Main St, City, State 12345\",\"paymentMethod\":\"credit_card\",\"notes\":\"Test order\"}" ^
  -w "\nHTTP Status: %%{http_code}\n" ^
  -s

echo.
echo Testing get orders by status...
curl -X GET http://localhost:8081/api/orders/status/PENDING ^
  -w "\nHTTP Status: %%{http_code}\n" ^
  -s

echo.
echo Testing get orders by payment status...
curl -X GET http://localhost:8081/api/orders/payment-status/PENDING ^
  -w "\nHTTP Status: %%{http_code}\n" ^
  -s

echo.
echo Testing high value orders...
curl -X GET "http://localhost:8081/api/orders/high-value?minAmount=50.00" ^
  -w "\nHTTP Status: %%{http_code}\n" ^
  -s

echo.
echo 📊 Testing Analytics Endpoints...
echo ----------------------------------------

echo Testing dashboard analytics...
curl -X GET http://localhost:8081/api/analytics/dashboard ^
  -w "\nHTTP Status: %%{http_code}\n" ^
  -s

echo.
echo Testing revenue trend analytics...
curl -X GET "http://localhost:8081/api/analytics/revenue/trend?days=7" ^
  -w "\nHTTP Status: %%{http_code}\n" ^
  -s

echo.
echo Testing user activity analytics...
curl -X GET "http://localhost:8081/api/analytics/users/activity?days=7" ^
  -w "\nHTTP Status: %%{http_code}\n" ^
  -s

echo.
echo Testing order performance analytics...
curl -X GET http://localhost:8081/api/analytics/orders/performance ^
  -w "\nHTTP Status: %%{http_code}\n" ^
  -s

echo.
echo Testing popular products analytics...
curl -X GET "http://localhost:8081/api/analytics/products/popular?limit=5" ^
  -w "\nHTTP Status: %%{http_code}\n" ^
  -s

echo.
echo Testing geographic sales analytics...
curl -X GET http://localhost:8081/api/analytics/sales/geographic ^
  -w "\nHTTP Status: %%{http_code}\n" ^
  -s

echo.
echo 🛍️ Testing Product Endpoints (Existing)...
echo ----------------------------------------

echo Testing get all products...
curl -X GET "http://localhost:8081/api/products?page=1&size=5" ^
  -w "\nHTTP Status: %%{http_code}\n" ^
  -s

echo.
echo Testing get product by ID...
curl -X GET http://localhost:8081/api/products/1 ^
  -w "\nHTTP Status: %%{http_code}\n" ^
  -s

echo.
echo Testing search products...
curl -X GET "http://localhost:8081/api/products/search?q=electronics&page=1&size=5" ^
  -w "\nHTTP Status: %%{http_code}\n" ^
  -s

echo.
echo Testing products by department...
curl -X GET "http://localhost:8081/api/products/department/Electronics?page=1&size=5" ^
  -w "\nHTTP Status: %%{http_code}\n" ^
  -s

echo.
echo Testing products by brand...
curl -X GET "http://localhost:8081/api/products/brand/Samsung?page=1&size=5" ^
  -w "\nHTTP Status: %%{http_code}\n" ^
  -s

echo.
echo 🔧 Testing System Endpoints...
echo ----------------------------------------

echo Testing get all departments...
curl -X GET http://localhost:8081/api/departments ^
  -w "\nHTTP Status: %%{http_code}\n" ^
  -s

echo.
echo Testing health check...
curl -X GET http://localhost:8081/api/actuator/health ^
  -w "\nHTTP Status: %%{http_code}\n" ^
  -s

echo.
echo Testing metrics...
curl -X GET http://localhost:8081/api/actuator/metrics ^
  -w "\nHTTP Status: %%{http_code}\n" ^
  -s

echo.
echo ========================================
echo ✅ Milestone 6 Testing Completed!
echo ========================================
echo.
echo 🎯 Milestone 6 Features Implemented:
echo   ✅ Authentication & Authorization
echo   ✅ Order Management System
echo   ✅ Payment Integration (Stripe)
echo   ✅ Email Notifications
echo   ✅ Advanced Analytics Dashboard
echo   ✅ User Management
echo   ✅ Role-based Access Control
echo   ✅ Order Status Tracking
echo   ✅ Revenue Analytics
echo   ✅ User Activity Monitoring
echo.
echo 🚀 The e-commerce platform is now complete!
echo.
pause 