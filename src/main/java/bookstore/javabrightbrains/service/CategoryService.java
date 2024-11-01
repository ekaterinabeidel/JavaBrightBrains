package bookstore.javabrightbrains.service;

import bookstore.javabrightbrains.dto.category.CategoryRequestDto;
import bookstore.javabrightbrains.dto.category.CategoryResponseDto;
import bookstore.javabrightbrains.entity.Category;
import bookstore.javabrightbrains.exception.DuplicateCategoryException;
import bookstore.javabrightbrains.exception.IdNotFoundException;
import bookstore.javabrightbrains.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryResponseDto save(CategoryRequestDto categoryDto) {
        if (categoryRepository.existsByName(categoryDto.getName())) {
            throw new DuplicateCategoryException("Category with name '" + categoryDto.getName() + "' already exists");
        }
        Category category = new Category();
        category.setName(categoryDto.getName());
        Category savedCategory = categoryRepository.save(category);
        return convertToResponseDto(savedCategory);
    }

    public CategoryResponseDto findById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Category not found"));
        return convertToResponseDto(category);
    }

    public List<CategoryResponseDto> findAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(this::convertToResponseDto).collect(Collectors.toList());
    }

    public CategoryResponseDto update(Long id, CategoryRequestDto categoryDto) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Category not found with id: " + id));
        category.setName(categoryDto.getName());
        Category updatedCategory = categoryRepository.save(category);
        return convertToResponseDto(updatedCategory);
    }

    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new IdNotFoundException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
    }

    public Category convertToEntity(CategoryRequestDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        return category;
    }

    public CategoryResponseDto convertToResponseDto(Category category) {
        CategoryResponseDto categoryDto = new CategoryResponseDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        return categoryDto;
    }

    public Category findEntityById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Category not found"));
    }
}
