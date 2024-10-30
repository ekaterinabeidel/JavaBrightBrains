package bookstore.javabrightbrains.service;

import bookstore.javabrightbrains.dto.BookDto;
import bookstore.javabrightbrains.dto.CategoryDto;
import bookstore.javabrightbrains.entity.Book;
import bookstore.javabrightbrains.entity.Category;
import bookstore.javabrightbrains.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryService categoryService;

    public BookDto save(BookDto bookDto) {
        Book book = convertToEntity(bookDto);
        // Загружаем полную информацию о категории из базы данных
        Category category = categoryService.convertToEntity(categoryService.findById(bookDto.getCategory().getId()));
        book.setCategory(category);
        Book savedBook = bookRepository.save(book);
        return convertToDto(savedBook);
    }

    public BookDto update(Long id, BookDto bookDto) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setDescription(bookDto.getDescription());
        book.setPrice(bookDto.getPrice());
        book.setDiscount(bookDto.getDiscount());
        // Загружаем полную информацию о категории из базы данных
        Category category = categoryService.convertToEntity(categoryService.findById(bookDto.getCategory().getId()));
        book.setCategory(category);
        book.setTotalStock(bookDto.getTotalStock());
        book.setImageLink(bookDto.getImageLink());
        Book updatedBook = bookRepository.save(book);
        return convertToDto(updatedBook);
    }

    public void delete(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        bookRepository.delete(book);
    }

    public List<BookDto> findAll() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private Book convertToEntity(BookDto bookDto) {
        Book book = new Book();
        book.setId(bookDto.getId());
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setDescription(bookDto.getDescription());
        book.setPrice(bookDto.getPrice());
        book.setDiscount(bookDto.getDiscount());
        book.setTotalStock(bookDto.getTotalStock());
        book.setImageLink(bookDto.getImageLink());

        CategoryDto categoryDto = bookDto.getCategory();
        if (categoryDto != null) {
            // Загружаем полную информацию о категории из базы данных
            book.setCategory(categoryService.convertToEntity(categoryService.findById(categoryDto.getId())));
        }
        return book;
    }

    private BookDto convertToDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setDescription(book.getDescription());
        bookDto.setPrice(book.getPrice());
        bookDto.setDiscount(book.getDiscount());
        bookDto.setTotalStock(book.getTotalStock());
        bookDto.setImageLink(book.getImageLink());

        Category category = book.getCategory();
        if (category != null) {
            bookDto.setCategory(categoryService.convertToDto(category));
        }
        return bookDto;
    }
}




