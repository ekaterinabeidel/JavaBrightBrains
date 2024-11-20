package bookstore.javabrightbrains.exception;

/**
 * Exception thrown when Local time is invalid.
 */
public class TimeInvalidException extends RuntimeException {
    /**
     * Constructs a new invalid time exception with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     */
    public TimeInvalidException(String message) {
        super(message);
    }
}
