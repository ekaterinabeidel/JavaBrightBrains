package bookstore.javabrightbrains.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import bookstore.javabrightbrains.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);
}
