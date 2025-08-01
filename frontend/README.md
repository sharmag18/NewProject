# 🛍️ E-commerce Frontend - Milestone 3

A modern React frontend application that displays products using the REST API from Milestone 2.

## 🚀 Features

### ✅ Required Features Implemented
1. **Products List View** - Display all products in a responsive grid format
2. **Product Detail View** - Show individual product details when clicked
3. **API Integration** - Fetch data from REST API endpoints
4. **Basic Styling** - Modern, responsive design with CSS
5. **Navigation** - Allow users to navigate between list and detail views

### 🎨 Additional Features
- **Responsive Design** - Works on desktop, tablet, and mobile
- **Loading States** - Show loading indicators during API calls
- **Error Handling** - Display user-friendly error messages
- **Pagination** - Navigate through product pages
- **Modern UI** - Clean, professional design with hover effects

## 🛠️ Technology Stack

- **Framework**: React 18.2.0
- **Routing**: React Router DOM 6.3.0
- **HTTP Client**: Axios 0.27.2
- **Styling**: CSS3 with modern design
- **Build Tool**: Create React App

## 📦 Installation

### Prerequisites
- Node.js (v14 or higher)
- npm or yarn
- Backend API running on `http://localhost:8081`

### Setup Steps

1. **Navigate to frontend directory**
   ```bash
   cd frontend
   ```

2. **Install dependencies**
   ```bash
   npm install
   ```

3. **Start the development server**
   ```bash
   npm start
   ```

4. **Open in browser**
   ```
   http://localhost:3000
   ```

## 🎯 API Integration

The frontend connects to the following API endpoints:

| Feature | API Endpoint | Method |
|---------|--------------|--------|
| Get All Products | `/api/products` | GET |
| Get Product by ID | `/api/products/{id}` | GET |
| Get Products by Department | `/api/products/department/{dept}` | GET |
| Get Products by Brand | `/api/products/brand/{brand}` | GET |
| Search Products | `/api/products/search?q={query}` | GET |

## 📱 User Interface

### Products List View
- **Grid Layout** - Responsive product cards
- **Product Cards** - Show name, brand, price, department
- **Pagination** - Navigate through product pages
- **Loading States** - Show loading indicators
- **Error Handling** - Display error messages with retry option

### Product Detail View
- **Detailed Information** - Complete product details
- **Back Navigation** - Return to product list
- **Responsive Layout** - Works on all screen sizes
- **Error Handling** - Handle product not found scenarios

## 🎨 Design Features

### Modern UI Elements
- **Gradient Headers** - Beautiful gradient backgrounds
- **Card Design** - Clean product cards with hover effects
- **Typography** - Modern font stack with proper hierarchy
- **Color Scheme** - Professional blue and purple theme
- **Responsive Grid** - Adapts to different screen sizes

### Interactive Elements
- **Hover Effects** - Cards lift on hover
- **Smooth Transitions** - CSS transitions for better UX
- **Button States** - Disabled states for pagination
- **Loading Animations** - Visual feedback during API calls

## 🔧 Configuration

### API Configuration
The API base URL is configured in `src/services/api.js`:
```javascript
const API_BASE_URL = 'http://localhost:8081/api';
```

### Proxy Configuration
The development proxy is configured in `package.json`:
```json
{
  "proxy": "http://localhost:8081"
}
```

## 🚀 Running the Application

### Development Mode
```bash
npm start
```
- Runs on `http://localhost:3000`
- Hot reload enabled
- Development tools available

### Production Build
```bash
npm run build
```
- Creates optimized production build
- Static files in `build/` directory

## 📊 Sample Data

The frontend displays 10 sample products:
1. **iPhone 14 Pro** (Apple, Electronics) - $999.99
2. **Cotton Crew Neck T-Shirt** (Nike, Clothing) - $24.99
3. **Vitamix Professional Blender** (Vitamix, Home & Garden) - $299.99
4. **Classic Blue Jeans** (Levi's, Clothing) - $79.99
5. **MacBook Air M2** (Apple, Electronics) - $1299.99
6. **Ceramic Coffee Mug** (Starbucks, Home & Garden) - $19.99
7. **Sony WH-1000XM4** (Sony, Electronics) - $349.99
8. **Air Jordan 1 Retro** (Nike, Clothing) - $169.99
9. **iPad Air** (Apple, Electronics) - $599.99
10. **Professional Chef Knife Set** (KitchenAid, Home & Garden) - $129.99

## 🔍 Testing the Complete Flow

1. **Start Backend API**
   ```bash
   # In the main project directory
   mvn spring-boot:run
   ```

2. **Start Frontend**
   ```bash
   # In the frontend directory
   npm start
   ```

3. **Test the Flow**
   - View products list at `http://localhost:3000`
   - Click on any product to see details
   - Navigate back to the list
   - Test pagination
   - Verify API integration

## 🎯 Milestone 3 Requirements Met

✅ **Products List View** - Grid format with pagination
✅ **Product Detail View** - Individual product details
✅ **API Integration** - Fetches data from REST API
✅ **Basic Styling** - Modern, responsive design
✅ **Navigation** - Between list and detail views

## 🚀 Next Steps

After completing Milestone 3, you'll be ready for:
- **Milestone 4**: Database Refactoring
- **Milestone 5**: Departments API
- **Milestone 6**: Department Filtering

**The frontend is now complete and ready for testing!** 🎉 