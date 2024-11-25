package bookstore.javabrightbrains.service;

import bookstore.javabrightbrains.dto.category.CategoryDto;
import bookstore.javabrightbrains.dto.category.CategoryRequestDto;
import bookstore.javabrightbrains.entity.Category;

import java.util.List;

public interface CategoryService {
    Category findEntityById(Long id);
    List<CategoryDto> findAll();
    CategoryDto save(CategoryRequestDto categoryRequestDto);
    CategoryDto update(Long id, CategoryRequestDto categoryRequestDto);
    void delete(Long id);
}
