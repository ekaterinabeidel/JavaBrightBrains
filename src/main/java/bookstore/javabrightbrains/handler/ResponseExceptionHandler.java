package bookstore.javabrightbrains.handler;

import bookstore.javabrightbrains.exception.DuplicateException;

import bookstore.javabrightbrains.exception.IdNotFoundException;
import bookstore.javabrightbrains.exception.InvalidQuantityException;
import bookstore.javabrightbrains.exception.NotEnoughBooksInStockException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ResponseExceptionHandler {
    /**
     * Handles the exception when the specified ID is not found.
     *
     * @param e The IdNotFoundException instance.
     * @return ResponseEntity containing the error message and status code.
     */
    @ExceptionHandler(IdNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<AppError> handleIdNotFoundException(IdNotFoundException e) {
        return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND, e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<AppError> handleDuplicateException(DuplicateException e) {
        return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotEnoughBooksInStockException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<AppError> handleNotEnoughBooksInStockException(NotEnoughBooksInStockException e) {
        return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidQuantityException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<AppError> handleInvalidQuantityException(InvalidQuantityException e) {
        return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST, e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
