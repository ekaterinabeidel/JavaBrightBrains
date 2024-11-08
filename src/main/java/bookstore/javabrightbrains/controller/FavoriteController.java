package bookstore.javabrightbrains.controller;

import bookstore.javabrightbrains.dto.book.BookShortResponseDto;
import bookstore.javabrightbrains.dto.favorite.FavoriteRequestDto;
import bookstore.javabrightbrains.service.FavoriteService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static bookstore.javabrightbrains.utils.Constants.USER_BASE_URL;

@RestController
@RequestMapping(USER_BASE_URL)
public class FavoriteController {
    @Autowired
    private FavoriteService favoriteServiceImpl;

    @Operation(summary = "Get list of books that are in favorites")
    @GetMapping("/favorites/{userId}")
    public ResponseEntity<List<BookShortResponseDto>> getAllBooks(@PathVariable Long userId) {
        List<BookShortResponseDto> favorites = favoriteServiceImpl.getFavorites(userId);
        return ResponseEntity.ok(favorites);
    }

    @Operation(summary = "Add book in favorites")
    @PostMapping("/favorites")
    public ResponseEntity<BookShortResponseDto> addFavorite(@RequestBody FavoriteRequestDto favorite) {
        BookShortResponseDto book = favoriteServiceImpl.saveFavorite(favorite);

        return ResponseEntity.ok(book);
    }

    @Operation(summary = "Delete book from favorites")
    @DeleteMapping("/favorites")
    public ResponseEntity<Void> deleteFavorite(@RequestBody FavoriteRequestDto favorite) {
        favoriteServiceImpl.deleteFavorite(favorite);
        return ResponseEntity.ok().build();
    }
}
