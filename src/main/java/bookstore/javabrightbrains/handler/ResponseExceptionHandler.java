package bookstore.javabrightbrains.handler;

import bookstore.javabrightbrains.exception.*;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

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

    /**
     * Handles validation errors for request bodies.
     *
     * @param ex The MethodArgumentNotValidException instance.
     * @return ResponseEntity containing the error messages and status code.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
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

    @ExceptionHandler(OrderCancellationNotAllowedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<AppError> handleOrderCancellationNotAllowedException(OrderCancellationNotAllowedException e) {
        return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TimeInvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<AppError> handleTimeInvalidException(TimeInvalidException e) {
        return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST, e.getMessage()), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(GroupByInvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<AppError> handleGroupByInvalidException(GroupByInvalidException e) {
        return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<AppError> handleAccessDeniedException (AccessDeniedException e) {
        return new ResponseEntity<>(new AppError(HttpStatus.FORBIDDEN, e.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(EmailDuplicateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<AppError> handleEmailDuplicatedException (EmailDuplicateException e) {
        return new ResponseEntity<>(new AppError(HttpStatus.CONFLICT, e.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmptyJwtTokenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<AppError> handleEmptyJwtTokenException (EmptyJwtTokenException e) {
        return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidJwtTokenException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<AppError> handleInvalidJwtTokenException (InvalidJwtTokenException e) {
        return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED, e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<AppError> handleExpiredJwtException() {
        String message = "JWT token has expired";
        return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED, message), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Map<String,String>> handleUsernameNotFoundException () {
        Map<String, String> response = new HashMap<>();
        response.put("message", MessagesException.USER_NOT_FOUND);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Map<String, String>> handleBadCredentialsException() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Invalid username or password");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
}
