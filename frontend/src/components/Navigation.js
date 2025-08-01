import React from 'react';
import { Link, useLocation } from 'react-router-dom';

const Navigation = () => {
  const location = useLocation();

  return (
    <nav className="nav">
      <div className="container">
        <div className="nav-container">
          <Link to="/" className="nav-brand">
            🛍️ E-commerce Products
          </Link>
          
          <div className="nav-links">
            <Link to="/" className={location.pathname === '/' ? 'active' : ''}>
              Products
            </Link>
            <a href="http://localhost:8081/api/products" target="_blank" rel="noopener noreferrer">
              API Docs
            </a>
          </div>
        </div>
      </div>
    </nav>
  );
};

export default Navigation; 