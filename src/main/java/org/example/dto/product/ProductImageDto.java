package org.example.dto.product;

import lombok.Data;

@Data
public class ProductImageDto {
    private Long id;
    private String name;   // Ім'я файлу зображення
    private int priority;
}

