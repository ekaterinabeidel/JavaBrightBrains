package bookstore.javabrightbrains.handler;

import bookstore.javabrightbrains.exception.DuplicateCategoryException;
import bookstore.javabrightbrains.exception.IdNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

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

    @ExceptionHandler(DuplicateCategoryException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<AppError> handleDuplicateCategoryException(DuplicateCategoryException e) {
        return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST, e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
