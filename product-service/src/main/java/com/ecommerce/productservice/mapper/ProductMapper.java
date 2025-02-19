package com.ecommerce.productservice.mapper;


import com.ecommerce.productservice.dto.request.ProductRequestDTO;
import com.ecommerce.productservice.dto.response.ProductResponseDTO;
import com.ecommerce.productservice.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    Product toEntity(ProductRequestDTO dto);
    ProductResponseDTO toResponseDTO(Product product);
}