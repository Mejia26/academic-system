package management_system.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Standardized API Error Response format.
 * Includes @JsonInclude to hide the 'validationErrors' field if it's null.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiErrorResponse(
        ZonedDateTime timestamp,
        int status,
        String error,
        String message,
        String path,
        List<String> validationErrors
) {}