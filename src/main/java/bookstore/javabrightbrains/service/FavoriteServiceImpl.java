package bookstore.javabrightbrains.service;

import bookstore.javabrightbrains.dto.book.BookShortResponseDto;
import bookstore.javabrightbrains.dto.favorite.FavoriteRequestDto;
import bookstore.javabrightbrains.entity.Book;
import bookstore.javabrightbrains.entity.Favorite;
import bookstore.javabrightbrains.entity.User;
import bookstore.javabrightbrains.exception.DuplicateException;
import bookstore.javabrightbrains.exception.IdNotFoundException;
import bookstore.javabrightbrains.exception.MessagesException;
import bookstore.javabrightbrains.repository.AppUserRepository;
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

    @Autowired
    private AppUserRepository appUserRepository;

    public List<BookShortResponseDto> getFavorites(Long userId) {
        List<Favorite> favorites = favoriteRepository.findAllByUserId(userId);

        appUserRepository.findById(userId).orElseThrow(
                () -> new IdNotFoundException(MessagesException.USER_NOT_FOUND)
        );
        return favorites.stream().map(favorite -> {
            Book book = favorite.getBook();
            return Utils.convertToBookShortResponseDto(book);
        }).toList();
    }

    public BookShortResponseDto saveFavorite(FavoriteRequestDto favoriteRequestDto) {
        Favorite favorite = new Favorite();
        Book book = bookRepository.findById(favoriteRequestDto.getBookId()).orElseThrow(
                () -> new IdNotFoundException(MessagesException.BOOK_NOT_FOUND)
        );
        User user = appUserRepository.findById(favoriteRequestDto.getUserId()).orElseThrow(
                () -> new IdNotFoundException(MessagesException.USER_NOT_FOUND)
        );

        Favorite favoriteDuplicate = favoriteRepository.findByUserAndBook(user, book);

        if (favoriteDuplicate != null) {
            throw new DuplicateException(MessagesException.FAVORITE_DUPLICATED);
        }

        favorite.setBook(book);
        favorite.setUser(user);

        favoriteRepository.save(favorite);

        return Utils.convertToBookShortResponseDto(book);
    }

    public void deleteFavorite(FavoriteRequestDto favoriteRequestDto) {
        Book book = bookRepository.findById(favoriteRequestDto.getBookId()).orElseThrow(
                () -> new IdNotFoundException(MessagesException.BOOK_NOT_FOUND)
        );

        User user = appUserRepository.findById(favoriteRequestDto.getUserId()).orElseThrow(
                () -> new IdNotFoundException(MessagesException.USER_NOT_FOUND)
        );

        Favorite favorite = favoriteRepository.findByUserAndBook(user, book);

        if (favorite == null) {
            throw new IdNotFoundException(MessagesException.FAVORITE_NOT_FOUND);
        }
        favoriteRepository.deleteById(favorite.getId());
    }
}
