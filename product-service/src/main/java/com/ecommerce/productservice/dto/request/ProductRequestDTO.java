package com.ecommerce.productservice.dto.request;

import lombok.*;

import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequestDTO {
    @NotBlank(message = "Product title is required.")
    private String productTitle;

    @NotBlank(message = "Product description is required.")
    private String productDescription;

    @NotNull(message = "Price unit is required.")
    private Double priceUnit;

    @NotNull(message = "Quantity is required.")
    private Integer quantity;
}