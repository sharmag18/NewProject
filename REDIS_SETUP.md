# 🔴 **Redis Setup Guide for Milestone 5**

This guide will help you set up Redis for the e-commerce application's caching layer.

## 📋 **What is Redis?**

Redis is an in-memory data structure store that we use for:
- **Caching** frequently accessed data
- **Session storage** (future use)
- **Rate limiting** data
- **Performance optimization**

## 🚀 **Installation Options**

### **Option 1: Docker (Recommended)**

#### **Install Docker:**
1. Download Docker Desktop from [docker.com](https://www.docker.com/products/docker-desktop)
2. Install and start Docker Desktop

#### **Run Redis with Docker:**
```bash
# Pull and run Redis container
docker run -d --name redis-ecommerce -p 6379:6379 redis:alpine

# Verify it's running
docker ps

# Test connection
docker exec -it redis-ecommerce redis-cli ping
```

### **Option 2: Windows Native Installation**

#### **Download Redis for Windows:**
1. Go to [Redis for Windows](https://github.com/microsoftarchive/redis/releases)
2. Download the latest release (e.g., `Redis-x64-3.0.504.msi`)
3. Install with default settings

#### **Start Redis Service:**
```bash
# Start Redis server
redis-server

# Or install as Windows service
redis-server --service-install
redis-server --service-start
```

### **Option 3: WSL2 (Windows Subsystem for Linux)**

#### **Install WSL2:**
```bash
# Enable WSL
wsl --install

# Install Ubuntu
wsl --install -d Ubuntu
```

#### **Install Redis in WSL2:**
```bash
# Update package list
sudo apt update

# Install Redis
sudo apt install redis-server

# Start Redis
sudo systemctl start redis-server

# Enable auto-start
sudo systemctl enable redis-server
```

## 🔧 **Configuration**

### **Default Redis Configuration:**
- **Host:** localhost
- **Port:** 6379
- **No authentication** (for development)
- **Memory limit:** Unlimited (for development)

### **Application Configuration:**
The application is configured to connect to Redis with these settings:
```properties
spring.redis.host=localhost
spring.redis.port=6379
spring.redis.timeout=2000ms
```

## 🧪 **Testing Redis Connection**

### **Test with redis-cli:**
```bash
# Connect to Redis
redis-cli

# Test basic commands
127.0.0.1:6379> ping
PONG

127.0.0.1:6379> set test "Hello Redis"
OK

127.0.0.1:6379> get test
"Hello Redis"

127.0.0.1:6379> exit
```

### **Test with Application:**
```bash
# Start the application
mvn spring-boot:run

# Check logs for Redis connection
# Look for: "Redis connection established"
```

## 📊 **Redis Monitoring**

### **Redis CLI Commands:**
```bash
# Connect to Redis
redis-cli

# Monitor all commands
MONITOR

# Get Redis info
INFO

# Get memory usage
INFO memory

# Get connected clients
CLIENT LIST

# Exit
exit
```

### **Redis GUI Tools:**
- **RedisInsight** (Official GUI): [redis.com/redis-enterprise/redis-insight](https://redis.com/redis-enterprise/redis-insight/)
- **Redis Desktop Manager**: [github.com/uglide/RedisDesktopManager](https://github.com/uglide/RedisDesktopManager)

## 🔍 **Troubleshooting**

### **Common Issues:**

#### **1. Redis Connection Refused:**
```bash
# Check if Redis is running
redis-cli ping

# If not running, start Redis
redis-server
```

#### **2. Port 6379 Already in Use:**
```bash
# Find what's using port 6379
netstat -ano | findstr :6379

# Kill the process
taskkill /PID <process_id> /F
```

#### **3. Docker Redis Issues:**
```bash
# Check Docker containers
docker ps

# Restart Redis container
docker restart redis-ecommerce

# Check Redis logs
docker logs redis-ecommerce
```

#### **4. WSL2 Redis Issues:**
```bash
# Check Redis service status
sudo systemctl status redis-server

# Restart Redis service
sudo systemctl restart redis-server

# Check Redis logs
sudo journalctl -u redis-server
```

### **Application Logs:**
```bash
# Check application logs for Redis errors
mvn spring-boot:run -Dspring-boot.run.arguments="--logging.level.org.springframework.data.redis=DEBUG"
```

## 🚀 **Performance Tips**

### **Redis Configuration:**
```bash
# Edit Redis config (if using native installation)
# Location: C:\Program Files\Redis\redis.windows.conf

# Set memory limit (optional)
maxmemory 256mb
maxmemory-policy allkeys-lru

# Enable persistence (optional)
save 900 1
save 300 10
save 60 10000
```

### **Application Caching:**
- **Product queries** cached for 10 minutes
- **Statistics** cached for 5 minutes
- **Cache eviction** on data updates
- **JSON serialization** for complex objects

## 📚 **Useful Commands**

### **Redis CLI:**
```bash
# Basic operations
SET key value
GET key
DEL key
EXISTS key

# List operations
LPUSH list value
RPOP list
LRANGE list 0 -1

# Hash operations
HSET hash field value
HGET hash field
HGETALL hash

# Set operations
SADD set value
SMEMBERS set
SREM set value

# Key management
KEYS *
TTL key
EXPIRE key seconds
```

### **Application Cache Management:**
```bash
# Clear all caches via API
curl -X POST "http://localhost:8081/api/products/cache/clear"

# Check cache statistics
curl -X GET "http://localhost:8081/api/actuator/metrics/cache.gets"
```

## 🎯 **Next Steps**

Once Redis is running:
1. **Start the application:** `mvn spring-boot:run`
2. **Test caching:** Make multiple requests to see cache hits
3. **Monitor performance:** Check actuator metrics
4. **Test rate limiting:** Make rapid requests to see 429 responses

---

**Redis is now ready for Milestone 5! 🚀** 