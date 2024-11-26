package bookstore.javabrightbrains.service;

import bookstore.javabrightbrains.dto.category.CategoryDto;
import bookstore.javabrightbrains.dto.category.CategoryRequestDto;
import bookstore.javabrightbrains.entity.Category;

import java.util.List;


public interface CategoryService {
    CategoryDto save(CategoryRequestDto categoryRequestDto);
    List<CategoryDto> findAll();
    CategoryDto update(Long id, CategoryRequestDto categoryRequestDto);
    void delete(Long id);
    Category findEntityById(Long id);
}
