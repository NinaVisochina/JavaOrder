package org.example.controller.api;

import lombok.AllArgsConstructor;
import org.example.dto.category.CategoryCreateDTO;
import org.example.dto.category.CategoryItemDTO;
import org.example.dto.category.CategoryUpdateDTO;
import org.example.mapper.CategoryMapper;
import org.example.model.CategoryEntity;
import org.example.repo.CategoryRepository;
import org.example.service.FileSaveFormat;
import org.example.storage.StorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/category")
public class CategoryController {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final StorageService storageService;

    // Метод для створення категорії з використанням CategoryCreateDTO
    @PostMapping(value="", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Integer> create(@ModelAttribute CategoryCreateDTO dto) {
        try {
            var entity = categoryMapper.categoryEntityByCategoryCreateDTO(dto);
            entity.setCreationTime(LocalDateTime.now());
            String fileName = storageService.saveImage(dto.getImage(), FileSaveFormat.WEBP);
            entity.setImage(fileName);
            categoryRepository.save(entity);
            return new ResponseEntity<>(entity.getId(), HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Метод для отримання всіх категорій
    @GetMapping("")
    public ResponseEntity<List<CategoryItemDTO>> getAllCategories() {
        var categories = categoryMapper.toDto(categoryRepository.findAll());
        return ResponseEntity.ok(categories);
    }

    // Метод для видалення категорії
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable int id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return ResponseEntity.noContent().build();  // Повертаємо статус 204 No Content після успішного видалення
        } else {
            return ResponseEntity.notFound().build();  // Повертаємо статус 404 якщо категорія не знайдена
        }
    }

    // Метод для оновлення категорії з використанням CategoryUpdateDTO
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CategoryEntity> updateCategory(
            @PathVariable int id,
            @ModelAttribute CategoryUpdateDTO dto) {
        return (ResponseEntity<CategoryEntity>) categoryRepository.findById(id).map(existingCategory -> {
            existingCategory.setName(dto.getName());
            existingCategory.setDescription(dto.getDescription());

            // Оновлюємо зображення, якщо було передано новий файл
            if (dto.getImage() != null && !dto.getImage().isEmpty()) {
                try {
                    String fileName = storageService.saveImage(dto.getImage(), FileSaveFormat.WEBP);
                    existingCategory.setImage(fileName);
                } catch (IOException e) {
                    e.printStackTrace();
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }

            categoryRepository.save(existingCategory);
            return ResponseEntity.ok(existingCategory);
        }).orElse(ResponseEntity.notFound().build());
    }
}
