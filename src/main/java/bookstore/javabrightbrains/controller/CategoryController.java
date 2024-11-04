package bookstore.javabrightbrains.controller;

import bookstore.javabrightbrains.dto.category.CategoryDto;
import bookstore.javabrightbrains.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

import static bookstore.javabrightbrains.utils.Constants.ADMIN_BASE_URL;

@RestController
@RequestMapping
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> categories = categoryService.findAll();
        return ResponseEntity.ok(categories);
    }

    @PostMapping(ADMIN_BASE_URL + "/categories")
    public ResponseEntity<CategoryDto> addCategory(@Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto createdCategoryDto = categoryService.save(categoryDto);
        return ResponseEntity.status(201).body(createdCategoryDto);
    }

    @PutMapping(ADMIN_BASE_URL + "/categories/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long categoryId, @Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto updatedCategoryDto = categoryService.update(categoryId, categoryDto);
        return ResponseEntity.ok(updatedCategoryDto);
    }

    @DeleteMapping(ADMIN_BASE_URL + "/categories/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId) {
        categoryService.delete(categoryId);
        return ResponseEntity.ok().build();
    }
}
