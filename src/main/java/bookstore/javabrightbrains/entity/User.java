package bookstore.javabrightbrains.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String name;
    private String surname;
    private String password;
    private String email;
    private String phone;
    private String role;

    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Favorite> favorites;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;

        if (!getId().equals(user.getId())) return false;
        if (!getName().equals(user.getName())) return false;
        if (!getSurname().equals(user.getSurname())) return false;
        if (!getEmail().equals(user.getEmail())) return false;
        if (!getPhone().equals(user.getPhone())) return false;
        return getRole().equals(user.getRole());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getSurname().hashCode();
        result = 31 * result + getEmail().hashCode();
        result = 31 * result + getPhone().hashCode();
        result = 31 * result + getRole().hashCode();
        return result;
    }
}


