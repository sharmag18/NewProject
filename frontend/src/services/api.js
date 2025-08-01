import axios from 'axios';

const API_BASE_URL = 'http://localhost:8081/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Request interceptor for logging
api.interceptors.request.use(
  (config) => {
    console.log(`Making ${config.method.toUpperCase()} request to: ${config.url}`);
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Response interceptor for error handling
api.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    console.error('API Error:', error.response?.data || error.message);
    return Promise.reject(error);
  }
);

export const productAPI = {
  // Get all products with pagination
  getProducts: async (page = 0, size = 10) => {
    try {
      const response = await api.get(`/products?page=${page}&size=${size}`);
      return response.data;
    } catch (error) {
      throw new Error(error.response?.data?.message || 'Failed to fetch products');
    }
  },

  // Get product by ID
  getProductById: async (id) => {
    try {
      const response = await api.get(`/products/${id}`);
      return response.data;
    } catch (error) {
      if (error.response?.status === 404) {
        throw new Error('Product not found');
      }
      throw new Error(error.response?.data?.message || 'Failed to fetch product');
    }
  },

  // Get products by department
  getProductsByDepartment: async (department, page = 0, size = 10) => {
    try {
      const response = await api.get(`/products/department/${department}?page=${page}&size=${size}`);
      return response.data;
    } catch (error) {
      throw new Error(error.response?.data?.message || 'Failed to fetch products by department');
    }
  },

  // Get products by brand
  getProductsByBrand: async (brand, page = 0, size = 10) => {
    try {
      const response = await api.get(`/products/brand/${brand}?page=${page}&size=${size}`);
      return response.data;
    } catch (error) {
      throw new Error(error.response?.data?.message || 'Failed to fetch products by brand');
    }
  },

  // Search products
  searchProducts: async (query, page = 0, size = 10) => {
    try {
      const response = await api.get(`/products/search?q=${encodeURIComponent(query)}&page=${page}&size=${size}`);
      return response.data;
    } catch (error) {
      throw new Error(error.response?.data?.message || 'Failed to search products');
    }
  },

  // Get all departments
  getDepartments: async () => {
    try {
      const response = await api.get('/products/departments');
      return response.data;
    } catch (error) {
      throw new Error(error.response?.data?.message || 'Failed to fetch departments');
    }
  },

  // Get all brands
  getBrands: async () => {
    try {
      const response = await api.get('/products/brands');
      return response.data;
    } catch (error) {
      throw new Error(error.response?.data?.message || 'Failed to fetch brands');
    }
  },

  // Get all categories
  getCategories: async () => {
    try {
      const response = await api.get('/products/categories');
      return response.data;
    } catch (error) {
      throw new Error(error.response?.data?.message || 'Failed to fetch categories');
    }
  },

  // Get product statistics
  getStats: async () => {
    try {
      const response = await api.get('/products/stats');
      return response.data;
    } catch (error) {
      throw new Error(error.response?.data?.message || 'Failed to fetch statistics');
    }
  },
};

export default api; 