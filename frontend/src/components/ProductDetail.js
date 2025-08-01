import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { productAPI } from '../services/api';

const ProductDetail = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [product, setProduct] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetchProduct();
  }, [id]);

  const fetchProduct = async () => {
    try {
      setLoading(true);
      setError(null);
      const data = await productAPI.getProductById(id);
      setProduct(data);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  const handleBack = () => {
    navigate('/');
  };

  if (loading) {
    return (
      <div className="container">
        <div className="loading">
          <p>Loading product details...</p>
        </div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="container">
        <div className="error">
          <h3>Error Loading Product</h3>
          <p>{error}</p>
          <button onClick={handleBack} style={{ marginTop: '1rem', padding: '0.5rem 1rem' }}>
            Back to Products
          </button>
        </div>
      </div>
    );
  }

  if (!product) {
    return (
      <div className="container">
        <div className="error">
          <h3>Product Not Found</h3>
          <p>The product you're looking for doesn't exist.</p>
          <button onClick={handleBack} style={{ marginTop: '1rem', padding: '0.5rem 1rem' }}>
            Back to Products
          </button>
        </div>
      </div>
    );
  }

  return (
    <div className="container">
      <div className="product-detail">
        <div className="product-detail-header">
          <button className="back-button" onClick={handleBack}>
            ← Back to Products
          </button>
        </div>

        <div className="product-detail-content">
          <div className="product-detail-image">
            📱
          </div>

          <div className="product-detail-info">
            <h2>{product.name}</h2>
            <p><strong>Brand:</strong> {product.brand}</p>
            <p><strong>Category:</strong> {product.category}</p>
            <p><strong>Department:</strong> {product.department}</p>
            
            <div className="product-detail-price">
              ${product.retailPrice}
            </div>

            <div className="product-detail-meta">
              <div className="meta-item">
                <div className="meta-label">Cost</div>
                <div className="meta-value">${product.cost}</div>
              </div>
              
              <div className="meta-item">
                <div className="meta-label">SKU</div>
                <div className="meta-value">{product.sku}</div>
              </div>
              
              <div className="meta-item">
                <div className="meta-label">Distribution Center</div>
                <div className="meta-value">#{product.distributionCenterId}</div>
              </div>
              
              <div className="meta-item">
                <div className="meta-label">Product ID</div>
                <div className="meta-value">#{product.id}</div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProductDetail; 