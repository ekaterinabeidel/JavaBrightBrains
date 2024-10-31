package bookstore.javabrightbrains.controller;

import bookstore.javabrightbrains.dto.CategoryDto;
import bookstore.javabrightbrains.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static bookstore.javabrightbrains.utils.Constants.ADMIN_BASE_URL;
import static bookstore.javabrightbrains.utils.Constants.USER_BASE_URL;

@RestController
@RequestMapping
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping(USER_BASE_URL + "/categories")
    public List<CategoryDto> getAllCategories() {
        return categoryService.findAll();
    }

    @PostMapping(ADMIN_BASE_URL + "/categories")
    public CategoryDto addCategory(@RequestBody CategoryDto categoryDto) {
        return categoryService.save(categoryDto);
    }

    @PutMapping(ADMIN_BASE_URL + "/categories/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long categoryId, @RequestBody CategoryDto categoryDto) {
        try {
            CategoryDto updatedCategory = categoryService.update(categoryId, categoryDto);
            return ResponseEntity.ok(updatedCategory);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping(ADMIN_BASE_URL + "/categories/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId) {
        try {
            categoryService.delete(categoryId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
