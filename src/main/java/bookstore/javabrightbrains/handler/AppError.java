package bookstore.javabrightbrains.handler;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppError {
    private HttpStatus statusCode;
    private String message;

}
