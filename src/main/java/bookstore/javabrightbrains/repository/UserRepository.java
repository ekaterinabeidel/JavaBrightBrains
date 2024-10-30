package bookstore.javabrightbrains.repository;

import bookstore.javabrightbrains.dto.user.UserDto;
import bookstore.javabrightbrains.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

@Repository
public class UserRepository {
    @Autowired
    private AppUserRepository userRepository;

    public User getUser(Long id) {
        return userRepository.findById(id).orElseGet(null);

    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User updateUser(Long id, UserDto userData) {
        User user = getUser(id);

        user.setName(userData.getName());
        user.setSurname(userData.getSurname());
        user.setEmail(userData.getEmail());
        user.setPhone(userData.getPhone());
        user.setUpdatedAt(Timestamp.from(Instant.now()));

        return userRepository.save(user);
    }

    public boolean deleteUser(Long id) {
        userRepository.deleteById(id);

        if (userRepository.existsById(id)) {
            return false;
        }

        return true;
    }
}
