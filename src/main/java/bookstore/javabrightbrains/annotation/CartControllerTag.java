package bookstore.javabrightbrains.annotation;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Tag(name = "Cart Controller", description = "APIs for managing manages the user's shopping cart")
public @interface CartControllerTag {
}
