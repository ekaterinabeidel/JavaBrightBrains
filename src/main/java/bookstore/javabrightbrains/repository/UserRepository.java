package bookstore.javabrightbrains.repository;

import bookstore.javabrightbrains.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
