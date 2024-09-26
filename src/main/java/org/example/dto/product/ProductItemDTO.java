package org.example.dto.product;

import lombok.Data;
import java.util.List;

@Data
public class ProductItemDTO {
    private Long id;
    private String name;
    private String description;
    private double price;
    private double discount;
    private List<ProductImageDto> images;  // Список зображень продукту
}

