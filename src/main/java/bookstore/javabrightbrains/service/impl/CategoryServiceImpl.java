package bookstore.javabrightbrains.service.impl;

import bookstore.javabrightbrains.dto.category.CategoryDto;
import bookstore.javabrightbrains.dto.category.CategoryRequestDto;
import bookstore.javabrightbrains.entity.Category;
import bookstore.javabrightbrains.exception.DuplicateException;
import bookstore.javabrightbrains.exception.IdNotFoundException;
import bookstore.javabrightbrains.exception.MessagesException;
import bookstore.javabrightbrains.repository.CategoryRepository;
import bookstore.javabrightbrains.service.CategoryService;
import bookstore.javabrightbrains.utils.MappingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryDto save(CategoryRequestDto categoryRequestDto) {
        if (categoryRepository.existsByName(categoryRequestDto.getName())) {
            throw new DuplicateException(MessagesException.CATEGORY_DUPLICATED);
        }
        Category category = new Category();
        category.setName(categoryRequestDto.getName());
        Category savedCategory = categoryRepository.save(category);
        return MappingUtils.convertCategoryEntityToCategoryDto(savedCategory);
    }

    public List<CategoryDto> findAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(MappingUtils::convertCategoryEntityToCategoryDto).collect(Collectors.toList());
    }

    public CategoryDto update(Long id, CategoryRequestDto categoryRequestDto) {
        Category category = categoryRepository.findById(id).orElseThrow(() ->
                new IdNotFoundException(MessagesException.CATEGORY_NOT_FOUND));
        category.setName(categoryRequestDto.getName());
        Category updatedCategory = categoryRepository.save(category);
        return MappingUtils.convertCategoryEntityToCategoryDto(updatedCategory);
    }

    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new IdNotFoundException(MessagesException.CATEGORY_NOT_FOUND);
        }
        categoryRepository.deleteById(id);
    }

    public Category findEntityById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() ->
                new IdNotFoundException(MessagesException.CATEGORY_NOT_FOUND));
    }
}
