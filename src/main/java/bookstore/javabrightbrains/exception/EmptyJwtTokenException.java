package bookstore.javabrightbrains.exception;

public class EmptyJwtTokenException extends RuntimeException {
    public EmptyJwtTokenException(String message) {
        super(message);
    }
}
