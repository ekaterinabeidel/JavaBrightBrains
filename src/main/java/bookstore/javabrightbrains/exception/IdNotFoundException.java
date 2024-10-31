package bookstore.javabrightbrains.exception;

/**
 * Exception thrown when an ID is not found.
 */
public class IdNotFoundException extends RuntimeException {

    /**
     * Constructs a new ID not found exception with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     */
    public IdNotFoundException(String message) {
        super(message);
    }
}
