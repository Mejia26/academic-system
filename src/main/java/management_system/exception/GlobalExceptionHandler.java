package management_system.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Global exception handler to intercept exceptions across the whole application
 * and return standardized, secure API responses.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles 404 Not Found errors (e.g., searching for a student that doesn't exist).
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleResourceNotFoundException(
    		ResourceNotFoundException ex, 
            HttpServletRequest request) {
            
        ApiErrorResponse response = new ApiErrorResponse(
                ZonedDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(), // Safe to expose as it is our custom message
                request.getRequestURI(),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles 409 Conflict errors (e.g., trying to create a duplicate record).
     */
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiErrorResponse> handleDuplicateResourceException(
            DuplicateResourceException ex, 
            HttpServletRequest request) {
            
        ApiErrorResponse response = new ApiErrorResponse(
                ZonedDateTime.now(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                ex.getMessage(), // Safe to expose as it is our custom message
                request.getRequestURI(),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    /**
     * Handles 400 Bad Request for validation errors (e.g., @NotBlank, @Email failed).
     * Extracts only the field names and validation messages, hiding internal Java details.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex, 
            HttpServletRequest request) {
            
        // Extract validation error messages safely
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();

        ApiErrorResponse response = new ApiErrorResponse(
                ZonedDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Validation failed for the submitted payload",
                request.getRequestURI(),
                errors
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * CATCH-ALL HANDLER: Intercepts all other unhandled exceptions (500 Internal Server Error).
     * This is CRITICAL for security. It logs the real error internally but returns 
     * a generic, safe message to the client, preventing information leakage.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleAllUncaughtExceptions(
            Exception ex, 
            HttpServletRequest request) {
            
        // 1. Log the full stack trace for internal debugging
        log.error("Unknown error occurred at {}: {}", request.getRequestURI(), ex.getMessage(), ex);

        // 2. Return a generic, safe message to the client
        ApiErrorResponse response = new ApiErrorResponse(
                ZonedDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                "An unexpected internal error occurred. Please contact support if the problem persists.",
                request.getRequestURI(),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}