package bookstore.javabrightbrains.controller;

import bookstore.javabrightbrains.dto.category.CategoryRequestDto;
import bookstore.javabrightbrains.dto.category.CategoryResponseDto;
import bookstore.javabrightbrains.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

import static bookstore.javabrightbrains.utils.Constants.ADMIN_BASE_URL;
import static bookstore.javabrightbrains.utils.Constants.USER_BASE_URL;

@RestController
@RequestMapping
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping(USER_BASE_URL + "/categories")
    public ResponseEntity<List<CategoryResponseDto>> getAllCategories() {
        List<CategoryResponseDto> categories = categoryService.findAll();
        return ResponseEntity.ok(categories);
    }

    @PostMapping(ADMIN_BASE_URL + "/categories")
    public ResponseEntity<CategoryResponseDto> addCategory(@Valid @RequestBody CategoryRequestDto categoryDto) {
        CategoryResponseDto createdCategoryDto = categoryService.save(categoryDto);
        return ResponseEntity.status(201).body(createdCategoryDto);
    }

    @PutMapping(ADMIN_BASE_URL + "/categories/{categoryId}")
    public ResponseEntity<CategoryResponseDto> updateCategory(@PathVariable Long categoryId, @Valid @RequestBody CategoryRequestDto categoryDto) {
        CategoryResponseDto updatedCategoryDto = categoryService.update(categoryId, categoryDto);
        return ResponseEntity.ok(updatedCategoryDto);
    }

    @DeleteMapping(ADMIN_BASE_URL + "/categories/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId) {
        categoryService.delete(categoryId);
        return ResponseEntity.ok().build();
    }
}
