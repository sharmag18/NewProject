@echo off
echo ========================================
echo Starting Milestone 6: Complete E-commerce Platform
echo ========================================
echo.

echo 🚀 Starting the application...
echo.

echo 📋 Milestone 6 Features:
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

echo 🔧 Prerequisites:
echo   - MySQL server running on localhost:3306
echo   - Redis server running on localhost:6379
echo   - Java 15+ installed
echo   - Maven installed
echo.

echo 🌐 Application will be available at:
echo   - API Base URL: http://localhost:8081/api
echo   - Health Check: http://localhost:8081/api/actuator/health
echo   - Metrics: http://localhost:8081/api/actuator/metrics
echo.

echo 📚 Default Users Created:
echo   - Admin: admin/admin123
echo   - User: user/user123  
echo   - Moderator: moderator/mod123
echo.

echo 🛍️ Test Endpoints:
echo   - Products: http://localhost:8081/api/products
echo   - Orders: http://localhost:8081/api/orders
echo   - Analytics: http://localhost:8081/api/analytics/dashboard
echo   - Auth: http://localhost:8081/api/auth/login
echo.

echo ⏳ Starting application...
echo.

mvn spring-boot:run

echo.
echo ✅ Application stopped.
pause 