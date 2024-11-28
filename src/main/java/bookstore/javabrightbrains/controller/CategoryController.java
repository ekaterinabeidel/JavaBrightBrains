package bookstore.javabrightbrains.controller;

import bookstore.javabrightbrains.annotation.AddCategory;
import bookstore.javabrightbrains.annotation.DeleteCategory;
import bookstore.javabrightbrains.annotation.GetAllCategories;
import bookstore.javabrightbrains.annotation.UpdateCategory;
import bookstore.javabrightbrains.dto.category.CategoryDto;
import bookstore.javabrightbrains.dto.category.CategoryRequestDto;
import bookstore.javabrightbrains.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

import static bookstore.javabrightbrains.utils.Constants.ADMIN_BASE_URL;
import static bookstore.javabrightbrains.utils.Constants.PUBLIC_BASE_URL;

@RestController
@RequestMapping
@Tag(name = "Category Controller", description = "APIs for managing categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping(PUBLIC_BASE_URL + "/categories")
    @GetAllCategories
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> categories = categoryService.findAll();
        return ResponseEntity.ok(categories);
    }

    @PostMapping(ADMIN_BASE_URL + "/categories")
    @AddCategory
    public ResponseEntity<CategoryDto> addCategory(@Valid @RequestBody CategoryRequestDto categoryRequestDto) {
        CategoryDto createdCategoryDto = categoryService.save(categoryRequestDto);
        return ResponseEntity.status(201).body(createdCategoryDto);
    }

    @PutMapping(ADMIN_BASE_URL + "/categories/{categoryId}")
    @UpdateCategory
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long categoryId, @Valid @RequestBody CategoryRequestDto categoryRequestDto) {
        CategoryDto updatedCategoryDto = categoryService.update(categoryId, categoryRequestDto);
        return ResponseEntity.ok(updatedCategoryDto);
    }

    @DeleteMapping(ADMIN_BASE_URL + "/categories/{categoryId}")
    @DeleteCategory
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId) {
        categoryService.delete(categoryId);
        return ResponseEntity.ok().build();
    }
}
