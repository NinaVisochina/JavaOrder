package org.example.controller.api;

import lombok.AllArgsConstructor;
import org.example.dto.product.ProductItemDTO;
import org.example.mapper.ProductMapper;
import org.example.repo.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    // Метод для отримання списку всіх продуктів
    @GetMapping
    public ResponseEntity<List<ProductItemDTO>> getAllProducts() {
        var products = productMapper.toDto(productRepository.findAll());
        return ResponseEntity.ok(products);
    }

    // Метод для отримання одного продукту за його ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductItemDTO> getProductById(@PathVariable Integer id) {
        return productRepository.findById(id)
                .map(productMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

