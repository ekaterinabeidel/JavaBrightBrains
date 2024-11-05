package bookstore.javabrightbrains.repository;

import bookstore.javabrightbrains.entity.Book;
import bookstore.javabrightbrains.entity.Favorite;
import bookstore.javabrightbrains.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findAllByUserId(Long userId);

    Favorite findByUserAndBook(User user, Book book);

}
