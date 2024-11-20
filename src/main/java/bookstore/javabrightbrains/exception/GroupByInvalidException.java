package bookstore.javabrightbrains.exception;

/**
 * Exception thrown when groupBy param is invalid.
 */
public class GroupByInvalidException extends RuntimeException{
    /**
     * Constructs a new invalid groupBy param exception with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     */
    public GroupByInvalidException(String message) {
        super(message);
    }
}
