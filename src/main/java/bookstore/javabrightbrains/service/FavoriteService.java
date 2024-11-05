package bookstore.javabrightbrains.service;

import bookstore.javabrightbrains.dto.book.BookShortResponseDto;
import bookstore.javabrightbrains.dto.favorite.FavoriteRequestDto;

import java.util.List;

public interface FavoriteService {
    List<BookShortResponseDto> getFavorites(Long userId);

    BookShortResponseDto saveFavorite(FavoriteRequestDto favoriteRequestDto);

    void deleteFavorite(FavoriteRequestDto favorite);
}
