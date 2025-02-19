package com.ecommerce.productservice.service.impl;


import com.ecommerce.productservice.dto.request.ProductRequestDTO;
import com.ecommerce.productservice.dto.response.ProductResponseDTO;
import com.ecommerce.productservice.entity.Product;
import com.ecommerce.productservice.mapper.ProductMapper;
import com.ecommerce.productservice.repository.ProductRepository;
import com.ecommerce.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO productRequest) {
        Product product = productMapper.toEntity(productRequest);
        product = productRepository.save(product);
        return productMapper.toResponseDTO(product);
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponseDTO getProductById(Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return productMapper.toResponseDTO(product);
    }

    @Override
    public ProductResponseDTO updateProduct(Integer productId, ProductRequestDTO productRequest) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setProductTitle(productRequest.getProductTitle());
        product.setProductDescription(productRequest.getProductDescription());
        product.setPriceUnit(productRequest.getPriceUnit());
        product.setQuantity(productRequest.getQuantity());

        return productMapper.toResponseDTO(productRepository.save(product));
    }

    @Override
    public void deleteProduct(Integer productId) {
        productRepository.deleteById(productId);
    }
}