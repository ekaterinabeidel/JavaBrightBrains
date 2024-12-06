package bookstore.javabrightbrains.annotation;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Tag(
        name = "User Controller",
        description = "APIs for managing user-related operations, " +
                "such as updating user details, retrieving user information, and deleting users"
)
public @interface UserControllerTag {
}
