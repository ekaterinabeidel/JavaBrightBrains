package bookstore.javabrightbrains.exception;

public class NotEnoughBooksInStockException extends RuntimeException {
    public NotEnoughBooksInStockException(String message) {
        super(message);
    }
}
