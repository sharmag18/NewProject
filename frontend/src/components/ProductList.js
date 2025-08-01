import React, { useState, useEffect } from 'react';
import ProductCard from './ProductCard';
import { productAPI } from '../services/api';

const ProductList = () => {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [totalItems, setTotalItems] = useState(0);

  useEffect(() => {
    fetchProducts();
  }, [currentPage]);

  const fetchProducts = async () => {
    try {
      setLoading(true);
      setError(null);
      const data = await productAPI.getProducts(currentPage, 10);
      setProducts(data.products);
      setTotalPages(data.totalPages);
      setTotalItems(data.totalItems);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  const handlePreviousPage = () => {
    if (currentPage > 0) {
      setCurrentPage(currentPage - 1);
    }
  };

  const handleNextPage = () => {
    if (currentPage < totalPages - 1) {
      setCurrentPage(currentPage + 1);
    }
  };

  if (loading) {
    return (
      <div className="container">
        <div className="loading">
          <p>Loading products...</p>
        </div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="container">
        <div className="error">
          <h3>Error Loading Products</h3>
          <p>{error}</p>
          <button onClick={fetchProducts} style={{ marginTop: '1rem', padding: '0.5rem 1rem' }}>
            Try Again
          </button>
        </div>
      </div>
    );
  }

  return (
    <div className="container">
      <div style={{ marginBottom: '2rem' }}>
        <h2>All Products ({totalItems} total)</h2>
        <p>Page {currentPage + 1} of {totalPages}</p>
      </div>

      <div className="products-grid">
        {products.map((product) => (
          <ProductCard key={product.id} product={product} />
        ))}
      </div>

      {totalPages > 1 && (
        <div style={{ 
          display: 'flex', 
          justifyContent: 'center', 
          gap: '1rem', 
          marginTop: '2rem',
          padding: '1rem'
        }}>
          <button
            onClick={handlePreviousPage}
            disabled={currentPage === 0}
            style={{
              padding: '0.5rem 1rem',
              border: 'none',
              borderRadius: '5px',
              backgroundColor: currentPage === 0 ? '#ccc' : '#667eea',
              color: 'white',
              cursor: currentPage === 0 ? 'not-allowed' : 'pointer'
            }}
          >
            Previous
          </button>
          
          <span style={{ padding: '0.5rem 1rem' }}>
            Page {currentPage + 1} of {totalPages}
          </span>
          
          <button
            onClick={handleNextPage}
            disabled={currentPage === totalPages - 1}
            style={{
              padding: '0.5rem 1rem',
              border: 'none',
              borderRadius: '5px',
              backgroundColor: currentPage === totalPages - 1 ? '#ccc' : '#667eea',
              color: 'white',
              cursor: currentPage === totalPages - 1 ? 'not-allowed' : 'pointer'
            }}
          >
            Next
          </button>
        </div>
      )}
    </div>
  );
};

export default ProductList; 