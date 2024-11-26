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


public interface CategoryService {
    CategoryDto save(CategoryRequestDto categoryRequestDto);
    List<CategoryDto> findAll();
    CategoryDto update(Long id, CategoryRequestDto categoryRequestDto);
    void delete(Long id);
    Category findEntityById(Long id);
}
