package com.ecommerce.productservice.service;

import com.ecommerce.productservice.dto.request.ProductRequestDTO;
import com.ecommerce.productservice.dto.response.ProductResponseDTO;

import java.util.List;

public interface ProductService {
    ProductResponseDTO createProduct(ProductRequestDTO productRequest);
    List<ProductResponseDTO> getAllProducts();
    ProductResponseDTO getProductById(Integer productId);
    ProductResponseDTO updateProduct(Integer productId, ProductRequestDTO productRequest);
    void deleteProduct(Integer productId);
}