package bookstore.javabrightbrains.service;

import bookstore.javabrightbrains.dto.book.BookShortResponseDto;
import bookstore.javabrightbrains.dto.favorite.FavoriteRequestDto;
import bookstore.javabrightbrains.entity.Book;
import bookstore.javabrightbrains.entity.Favorite;
import bookstore.javabrightbrains.entity.User;
import bookstore.javabrightbrains.exception.DuplicateException;
import bookstore.javabrightbrains.exception.IdNotFoundException;
import bookstore.javabrightbrains.repository.BookRepository;
import bookstore.javabrightbrains.repository.FavoriteRepository;
import bookstore.javabrightbrains.repository.UserRepository;
import bookstore.javabrightbrains.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteServiceImpl implements FavoriteService {
    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired

    private UserRepository userRepository;

    public List<BookShortResponseDto> getFavorites(Long userId) {
        List<Favorite> favorites = favoriteRepository.findAllByUserId(userId);

        return favorites.stream().map(favorite -> {
            Book book = favorite.getBook();
            return Utils.convertToBookShortResponseDto(book);
        }).toList();
    }

    public BookShortResponseDto saveFavorite(FavoriteRequestDto favoriteRequestDto) {
        Favorite favorite = new Favorite();
        Book book = bookRepository.findById(favoriteRequestDto.getBookId()).orElseThrow(
                () -> new IdNotFoundException("Book not found")
        );
        User user = userRepository.findById(favoriteRequestDto.getUserId()).orElseThrow(
                () -> new IdNotFoundException("User not found")
        );

        Favorite favoriteDuplicate = favoriteRepository.findByUserAndBook(user, book);

        if (favoriteDuplicate != null) {
            throw new DuplicateException("This book is already in favorite");
        }

        favorite.setBook(book);
        favorite.setUser(user);

        favoriteRepository.save(favorite);

        return Utils.convertToBookShortResponseDto(book);
    }

    public void deleteFavorite(FavoriteRequestDto favoriteRequestDto) {
        Book book = bookRepository.findById(favoriteRequestDto.getBookId()).orElseThrow(
                () -> new IdNotFoundException("Book not found")
        );

        User user = userRepository.findById(favoriteRequestDto.getUserId()).orElseThrow(
                () -> new IdNotFoundException("User not found")
        );

        Favorite favorite = favoriteRepository.findByUserAndBook(user, book);

        if (favorite == null) {
            throw new IdNotFoundException("Favorite not found");
        }
        favoriteRepository.deleteById(favorite.getId());
    }
}
