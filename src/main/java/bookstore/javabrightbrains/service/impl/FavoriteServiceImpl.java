package bookstore.javabrightbrains.service.impl;

import bookstore.javabrightbrains.dto.book.BookShortResponseDto;
import bookstore.javabrightbrains.dto.favorite.FavoriteRequestDto;
import bookstore.javabrightbrains.entity.Book;
import bookstore.javabrightbrains.entity.Favorite;
import bookstore.javabrightbrains.entity.User;
import bookstore.javabrightbrains.exception.DuplicateException;
import bookstore.javabrightbrains.exception.IdNotFoundException;
import bookstore.javabrightbrains.exception.MessagesException;
import bookstore.javabrightbrains.repository.FavoriteRepository;
import bookstore.javabrightbrains.service.AppUserService;
import bookstore.javabrightbrains.service.BookService;
import bookstore.javabrightbrains.service.FavoriteService;
import bookstore.javabrightbrains.service.JwtSecurityService;
import bookstore.javabrightbrains.utils.MappingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteServiceImpl implements FavoriteService {
    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    AppUserService userService;

    @Autowired
    BookService bookService;

    @Autowired
    private JwtSecurityService jwtSecurityService;


    public List<BookShortResponseDto> getFavorites(Long userId) {
        List<Favorite> favorites = favoriteRepository.findAllByUserId(userId);

        userService.getUserById(userId);
        jwtSecurityService.validateUserAccess(userId);
        return favorites.stream().map(favorite -> {
            Book book = favorite.getBook();
            return MappingUtils.convertToBookShortResponseDto(book);
        }).toList();
    }

    public BookShortResponseDto saveFavorite(FavoriteRequestDto favoriteRequestDto) {
        Favorite favorite = new Favorite();
        Book book = bookService.getBookById(favoriteRequestDto.getBookId());
        User user = userService.getUserById(favoriteRequestDto.getUserId());

        jwtSecurityService.validateUserAccess(favoriteRequestDto.getUserId());

        Favorite favoriteDuplicate = favoriteRepository.findByUserAndBook(user, book);

        if (favoriteDuplicate != null) {
            throw new DuplicateException(MessagesException.FAVORITE_DUPLICATED);
        }

        favorite.setBook(book);
        favorite.setUser(user);

        favoriteRepository.save(favorite);

        return MappingUtils.convertToBookShortResponseDto(book);
    }

    public void deleteFavorite(FavoriteRequestDto favoriteRequestDto) {
        Book book = bookService.getBookById(favoriteRequestDto.getBookId());
        User user = userService.getUserById(favoriteRequestDto.getUserId());

        jwtSecurityService.validateUserAccess(favoriteRequestDto.getUserId());

        Favorite favorite = favoriteRepository.findByUserAndBook(user, book);

        if (favorite == null) {
            throw new IdNotFoundException(MessagesException.FAVORITE_NOT_FOUND);
        }
        favoriteRepository.deleteById(favorite.getId());
    }
}
