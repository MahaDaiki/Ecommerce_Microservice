package com.ecommerce.productservice.dto.response;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponseDTO {
    private Integer productId;
    private String productTitle;
    private String productDescription;
    private Double priceUnit;
    private Integer quantity;
}