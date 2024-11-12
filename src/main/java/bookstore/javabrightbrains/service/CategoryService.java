package bookstore.javabrightbrains.service;

import bookstore.javabrightbrains.dto.category.CategoryDto;
import bookstore.javabrightbrains.dto.category.CategoryRequestDto;
import bookstore.javabrightbrains.entity.Category;
import bookstore.javabrightbrains.exception.DuplicateException;
import bookstore.javabrightbrains.exception.IdNotFoundException;
import bookstore.javabrightbrains.exception.MessagesException;
import bookstore.javabrightbrains.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryDto save(CategoryRequestDto categoryRequestDto) {
        if (categoryRepository.existsByName(categoryRequestDto.getName())) {
            throw new DuplicateException(MessagesException.CATEGORY_DUPLICATED);
        }
        Category category = new Category();
        category.setName(categoryRequestDto.getName());
        Category savedCategory = categoryRepository.save(category);
        return convertToDto(savedCategory);
    }

    public List<CategoryDto> findAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public CategoryDto update(Long id, CategoryRequestDto categoryRequestDto) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new IdNotFoundException(MessagesException.CATEGORY_NOT_FOUND));
        category.setName(categoryRequestDto.getName());
        Category updatedCategory = categoryRepository.save(category);
        return convertToDto(updatedCategory);
    }

    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new IdNotFoundException(MessagesException.CATEGORY_NOT_FOUND);
        }
        categoryRepository.deleteById(id);
    }


    public CategoryDto convertToDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        return categoryDto;
    }

    public Category findEntityById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new IdNotFoundException(MessagesException.CATEGORY_NOT_FOUND));
    }
}
