package bookstore.javabrightbrains.exception;

public class EmailDuplicateException extends RuntimeException {
    public EmailDuplicateException(String errorMessage) {
        super(errorMessage);
    }
}
