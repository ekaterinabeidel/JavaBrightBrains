package bookstore.javabrightbrains.service;

import bookstore.javabrightbrains.dto.book.*;
import bookstore.javabrightbrains.entity.Book;
import bookstore.javabrightbrains.entity.Category;
import bookstore.javabrightbrains.exception.IdNotFoundException;
import bookstore.javabrightbrains.exception.MessagesException;
import bookstore.javabrightbrains.repository.BookRepository;
import bookstore.javabrightbrains.utils.MappingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Random;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private MappingUtils mappingUtils;

    public BookResponseDto save(BookRequestDto bookDto) {
        Category category = categoryService.findEntityById(bookDto.getCategoryId());
        if (category == null) {
            throw new IdNotFoundException(MessagesException.CATEGORY_NOT_FOUND);
        }
        Book book = mappingUtils.convertToBookEntity(bookDto);
        book.setCategory(category);
        Book savedBook = bookRepository.save(book);
        return mappingUtils.convertToBookResponseDto(savedBook);
    }

    public BookResponseDto update(Long id, BookRequestDto bookDto) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new IdNotFoundException(MessagesException.BOOK_NOT_FOUND));
        Category category = categoryService.findEntityById(bookDto.getCategoryId());
        if (category == null) {
            throw new IdNotFoundException(MessagesException.CATEGORY_NOT_FOUND);
        }
        book = mappingUtils.updateBookFromDto(book, bookDto);
        book.setCategory(category);
        Book updatedBook = bookRepository.save(book);
        return mappingUtils.convertToBookResponseDto(updatedBook);
    }


    public void delete(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new IdNotFoundException(MessagesException.BOOK_NOT_FOUND));
        bookRepository.delete(book);
    }

    public PageResponseDto<BookShortResponseDto> findAll(
            int pageNum,
            int pageSize,
            Long categoryId,
            Integer minPrice,
            Integer maxPrice,
            Boolean isDiscount,
            String sortBy,
            String sortDirect
    ) {

        Category category = null;
        if (categoryId != null) {
            category = categoryService.findEntityById(categoryId);

            if (category == null) {
                throw new IdNotFoundException(MessagesException.CATEGORY_NOT_FOUND);
            }
        }


        BookFilterDto filter = new BookFilterDto(
                category,
                minPrice,
                maxPrice,
                isDiscount
        );
        List<String> fields = Arrays.stream(Book.class.getDeclaredFields()).map(Field::getName).toList();

        if (sortBy != null && !fields.contains(sortBy)) {
            throw new IllegalArgumentException("There is no such field to sort by.");
        }
        Sort sort = null;
        String sortByNew = sortBy != null ? sortBy : "title";


        if (sortDirect != null && sortDirect.equals("desc")) {
            sort = Sort.by(sortByNew).descending();
        } else {
            sort = Sort.by(sortByNew).ascending();
        }


        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
        Page<Book> books = bookRepository.findByFilter(filter, pageable);

        PageResponseDto<BookShortResponseDto> bookPageResponseDto = new PageResponseDto<>();
        bookPageResponseDto.setContent(books.getContent().stream().map(mappingUtils::convertToBookShortResponseDto).collect(Collectors.toList()));
        bookPageResponseDto.setTotal(books.getTotalElements());
        bookPageResponseDto.setPageNumber(pageable.getPageNumber() + 1);
        bookPageResponseDto.setPageSize(pageable.getPageSize());

        return bookPageResponseDto;
    }

    public BookResponseDto findById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new IdNotFoundException(MessagesException.BOOK_NOT_FOUND));
        return mappingUtils.convertToBookResponseDto(book);
    }

    public BookResponseDto getDailyProduct() {
        List<Book> topDiscountBooks = bookRepository.findFirstByOrderByDiscountDesc();

        if (topDiscountBooks.isEmpty()) {
            return null;
        }

        int maxDiscount = topDiscountBooks.get(0).getDiscount();
        List<Book> booksWithMaxDiscount = bookRepository.findByDiscount(maxDiscount);

        if (booksWithMaxDiscount.isEmpty() || maxDiscount <= 0) {
            return null;
        }

        Book selectedBook = booksWithMaxDiscount.size() == 1
                ? booksWithMaxDiscount.get(0)
                : booksWithMaxDiscount.get(new Random().nextInt(booksWithMaxDiscount.size()));

        return mappingUtils.convertToBookResponseDto(selectedBook);
    }
}