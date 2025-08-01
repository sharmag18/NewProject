# 🛒 **E-commerce API Project**

A comprehensive e-commerce REST API built with **Spring Boot**, **MySQL**, and **Redis**, featuring advanced search, caching, performance monitoring, rate limiting, authentication, order management, payment integration, and analytics.

## 📋 **Project Overview**

This project demonstrates a complete e-commerce backend implementation with progressive milestones, each adding new features and capabilities:

- **Milestone 1**: Basic database setup and CSV data loading
- **Milestone 2**: REST API with Spring Boot
- **Milestone 3**: React frontend integration
- **Milestone 4**: Database normalization and refactoring
- **Milestone 5**: Advanced features and performance optimization
- **Milestone 6**: Complete E-commerce Platform ⭐ **CURRENT**

---

## 🚀 **Milestone 6: Complete E-commerce Platform**

### **New Features Added:**

#### **1. Authentication & Authorization**
- **JWT-based authentication** with secure token management
- **Role-based access control** (USER, ADMIN, MODERATOR)
- **User registration and login** endpoints
- **Password encryption** with BCrypt
- **Session management** with stateless JWT tokens

#### **2. Order Management System**
- **Complete order lifecycle** from creation to delivery
- **Order status tracking** (PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED)
- **Payment status management** (PENDING, COMPLETED, FAILED)
- **Order items and quantities** with price calculations
- **Tax and shipping calculations** with configurable rates
- **Order history and tracking** for users

#### **3. Payment Integration**
- **Stripe payment processing** with secure API integration
- **Payment intent creation** and verification
- **Refund processing** capabilities
- **Multiple payment methods** support
- **Payment status tracking** and notifications

#### **4. Email Notifications**
- **Order confirmation emails** with detailed order information
- **Payment confirmation emails** with transaction details
- **Order status update notifications** for tracking
- **Configurable email templates** with dynamic content
- **SMTP integration** with Gmail support

#### **5. Advanced Analytics Dashboard**
- **Real-time revenue analytics** with trend analysis
- **User activity monitoring** and registration statistics
- **Order performance metrics** with status distribution
- **Product popularity tracking** and category analytics
- **Geographic sales analysis** capabilities
- **Custom date range filtering** for all analytics

#### **6. User Management**
- **User profile management** with personal information
- **Account activation/deactivation** capabilities
- **Last login tracking** and activity monitoring
- **User role assignment** and management
- **Account security** with password validation

#### **7. Enhanced API Endpoints**
- **Authentication endpoints** (/api/auth/*)
- **Order management endpoints** (/api/orders/*)
- **Analytics endpoints** (/api/analytics/*)
- **User management endpoints** (integrated)
- **Payment processing endpoints** (integrated)

---

## 🛠 **Technology Stack**

### **Backend:**
- **Java 15** - Core language
- **Spring Boot 2.7.0** - Application framework
- **Spring Security** - Authentication and authorization
- **Spring Data JPA** - Data access layer
- **MySQL 8.0** - Primary database
- **Redis 6.x** - Caching layer
- **Stripe API** - Payment processing
- **JWT** - Token-based authentication
- **Bucket4j** - Rate limiting
- **Spring Boot Actuator** - Monitoring
- **Bean Validation** - Input validation
- **Spring Mail** - Email notifications

### **Frontend:**
- **React 18** - UI framework
- **React Router DOM** - Client-side routing
- **Axios** - HTTP client
- **CSS3** - Styling

### **Build Tools:**
- **Maven** - Dependency management
- **npm** - Frontend package manager

---

## 📦 **Prerequisites**

### **Required Software:**
1. **Java 15** or higher
2. **Maven 3.6+**
3. **MySQL 8.0**
4. **Redis 6.x** (for caching)
5. **Node.js 14+** (for frontend)
6. **npm** or **yarn**

### **External Services:**
1. **Stripe Account** (for payment processing)
2. **SMTP Email Service** (for notifications)

### **Database Setup:**
```sql
-- Create database
CREATE DATABASE ecommerce;
USE ecommerce;

-- The application will automatically create all tables
-- using JPA/Hibernate with ddl-auto=create-drop
```

---

## 🚀 **Quick Start**

### **1. Clone and Setup:**
```bash
git clone <repository-url>
cd CSVtoMySQL
```

### **2. Configure Database:**
```bash
# Start MySQL and create database
mysql -u root -p
CREATE DATABASE ecommerce;
```

### **3. Configure Application:**
Edit `src/main/resources/application.properties`:
```properties
# Update database credentials
spring.datasource.username=your_username
spring.datasource.password=your_password

# Update Stripe keys
stripe.secret-key=sk_test_your_stripe_secret_key
stripe.publishable-key=pk_test_your_stripe_publishable_key

# Update email settings
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
```

### **4. Start Services:**
```bash
# Start Redis
redis-server

# Start MySQL (if not running as service)
mysql.server start
```

### **5. Run Application:**
```bash
# Windows
run_milestone6.bat

# Linux/Mac
mvn spring-boot:run
```

### **6. Test the API:**
```bash
# Test authentication
curl -X POST http://localhost:8081/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'

# Test products
curl http://localhost:8081/api/products

# Test analytics
curl http://localhost:8081/api/analytics/dashboard
```

---

## 📚 **API Documentation**

### **Authentication Endpoints:**
```
POST /api/auth/login - User login
POST /api/auth/register - User registration
GET /api/auth/profile - Get user profile
```

### **Product Endpoints:**
```
GET /api/products - Get all products (paginated)
GET /api/products/{id} - Get product by ID
GET /api/products/search?q={keyword} - Search products
GET /api/products/department/{department} - Get by department
GET /api/products/brand/{brand} - Get by brand
```

### **Order Endpoints:**
```
POST /api/orders - Create new order
GET /api/orders/{id} - Get order by ID
GET /api/orders/number/{orderNumber} - Get by order number
GET /api/orders/user/{userId} - Get user orders
PUT /api/orders/{id}/status - Update order status
POST /api/orders/{id}/payment - Process payment
```

### **Analytics Endpoints:**
```
GET /api/analytics/dashboard - Dashboard analytics
GET /api/analytics/revenue/trend - Revenue trends
GET /api/analytics/users/activity - User activity
GET /api/analytics/orders/performance - Order performance
GET /api/analytics/products/popular - Popular products
```

### **System Endpoints:**
```
GET /api/departments - Get all departments
GET /api/actuator/health - Health check
GET /api/actuator/metrics - Application metrics
```

---

## 🔐 **Default Users**

The application creates default users on startup:

| Username | Password | Role | Description |
|----------|----------|------|-------------|
| admin | admin123 | ADMIN | Full system access |
| user | user123 | USER | Regular user access |
| moderator | mod123 | MODERATOR | Limited admin access |

---

## 🧪 **Testing**

### **Run All Tests:**
```bash
# Windows
test_milestone6.bat

# Manual testing
curl -X POST http://localhost:8081/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'
```

### **Test Order Creation:**
```bash
curl -X POST http://localhost:8081/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "items": [{"productId": 1, "quantity": 2}],
    "shippingAddress": "123 Main St, City, State 12345",
    "billingAddress": "123 Main St, City, State 12345",
    "paymentMethod": "credit_card",
    "notes": "Test order"
  }'
```

---

## 🔧 **Configuration**

### **Application Properties:**
```properties
# Database
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce
spring.datasource.username=root
spring.datasource.password=your_password

# Redis Cache
spring.redis.host=localhost
spring.redis.port=6379

# Security
app.jwt.secret=your-secret-key-here
app.jwt.expiration=86400000

# Email
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password

# Payment
stripe.secret-key=sk_test_your_stripe_secret_key
stripe.publishable-key=pk_test_your_stripe_publishable_key
```

---

## 🔧 **Troubleshooting**

### **Common Issues:**

#### **Authentication Issues:**
```bash
# Check if users exist
curl -X GET http://localhost:8081/api/auth/profile

# Verify JWT token
curl -H "Authorization: Bearer YOUR_TOKEN" \
  http://localhost:8081/api/auth/profile
```

#### **Payment Processing Issues:**
```bash
# Check Stripe configuration
curl -X POST http://localhost:8081/api/orders/1/payment \
  -H "Content-Type: application/json" \
  -d '{"paymentIntentId": "pi_test_123"}'
```

#### **Email Notification Issues:**
```bash
# Check SMTP configuration
# Verify email credentials in application.properties
```

#### **Database Connection Issues:**
```bash
# Check MySQL service
mysql -u root -p

# Verify database exists
SHOW DATABASES;
USE ecommerce;
SHOW TABLES;
```

---

## 📚 **Learning Resources**

### **Spring Security:**
- [Spring Security Reference](https://docs.spring.io/spring-security/site/docs/current/reference/html5/)
- [JWT with Spring Security](https://spring.io/guides/tutorials/spring-security-and-angular-js/)

### **Payment Processing:**
- [Stripe Documentation](https://stripe.com/docs)
- [Stripe Java Library](https://github.com/stripe/stripe-java)

### **Email Integration:**
- [Spring Boot Mail](https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-email)

---

## 🤝 **Contributing**

1. **Fork** the repository
2. **Create** a feature branch
3. **Make** your changes
4. **Test** thoroughly
5. **Submit** a pull request

---

## 📄 **License**

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 🎯 **Project Status**

**✅ Milestone 6 Complete!**

The e-commerce platform now includes:
- ✅ **Authentication & Authorization**
- ✅ **Order Management System**
- ✅ **Payment Integration**
- ✅ **Email Notifications**
- ✅ **Advanced Analytics Dashboard**
- ✅ **User Management**
- ✅ **Role-based Access Control**
- ✅ **Order Status Tracking**
- ✅ **Revenue Analytics**
- ✅ **User Activity Monitoring**

**🚀 The e-commerce platform is now complete and production-ready!**

---

**Happy Coding! 🚀** 