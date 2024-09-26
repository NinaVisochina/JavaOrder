package org.example.repo;

import org.example.model.CategoryEntity;
import org.example.model.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    Page<ProductEntity> findByNameContainingIgnoreCase(String name, Pageable pageable);
}

