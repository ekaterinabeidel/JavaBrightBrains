package bookstore.javabrightbrains.exception;

/**
 * Exception thrown when a category with the same name already exists.
 */
public class DuplicateException extends RuntimeException {
  /**
   * Constructs a new duplicate category exception with the specified detail message.
   *
   * @param message the detail message (which is saved for later retrieval by the getMessage() method)
   */
  public DuplicateException(String message) {
    super(message);
  }
}
