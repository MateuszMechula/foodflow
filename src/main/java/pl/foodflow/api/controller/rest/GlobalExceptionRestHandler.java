package pl.foodflow.api.controller.rest;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.foodflow.api.dto.ExceptionMessage;
import pl.foodflow.business.exceptions.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionRestHandler extends ResponseEntityExceptionHandler {

    private static final Map<Class<?>, HttpStatus> EXCEPTION_STATUS = new HashMap<>();

    static {
        EXCEPTION_STATUS.put(ConstraintViolationException.class, HttpStatus.BAD_REQUEST);
        EXCEPTION_STATUS.put(DataIntegrityViolationException.class, HttpStatus.BAD_REQUEST);
        EXCEPTION_STATUS.put(EntityNotFoundException.class, HttpStatus.NOT_FOUND);
        EXCEPTION_STATUS.put(NotFoundException.class, HttpStatus.NOT_FOUND);
        EXCEPTION_STATUS.put(CategoryItemNotFoundException.class, HttpStatus.NOT_FOUND);
        EXCEPTION_STATUS.put(CustomerNotFoundException.class, HttpStatus.NOT_FOUND);
        EXCEPTION_STATUS.put(InvalidAddressException.class, HttpStatus.BAD_REQUEST);
        EXCEPTION_STATUS.put(MenuCategoryNotFoundException.class, HttpStatus.NOT_FOUND);
        EXCEPTION_STATUS.put(MenuNotFoundException.class, HttpStatus.NOT_FOUND);
        EXCEPTION_STATUS.put(MissingImageFileException.class, HttpStatus.BAD_REQUEST);
        EXCEPTION_STATUS.put(OrderItemsNotFoundException.class, HttpStatus.NOT_FOUND);
        EXCEPTION_STATUS.put(OrderRecordNotFoundException.class, HttpStatus.NOT_FOUND);
        EXCEPTION_STATUS.put(OrdersNotFoundException.class, HttpStatus.NOT_FOUND);
        EXCEPTION_STATUS.put(OwnerNotFoundException.class, HttpStatus.NOT_FOUND);
        EXCEPTION_STATUS.put(RestaurantAddressNotFoundException.class, HttpStatus.NOT_FOUND);
        EXCEPTION_STATUS.put(RestaurantAlreadyExistsException.class, HttpStatus.BAD_REQUEST);
        EXCEPTION_STATUS.put(RestaurantNotFound.class, HttpStatus.NOT_FOUND);
        EXCEPTION_STATUS.put(MenuAlreadyExists.class, HttpStatus.BAD_REQUEST);
        EXCEPTION_STATUS.put(UsernameNotFoundException.class, HttpStatus.NOT_FOUND);
        EXCEPTION_STATUS.put(UserNotFoundException.class, HttpStatus.NOT_FOUND);

    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            @NonNull Exception exception,
            @Nullable Object body,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode statusCode,
            @NonNull WebRequest request
    ) {
        final String errorId = UUID.randomUUID().toString();
        log.error("Exception: ID={}, HttpStatus={}", errorId, statusCode, exception);

        return super.handleExceptionInternal(exception, ExceptionMessage.of(errorId), headers, statusCode, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handle(final Exception exception) {
        return doHandle(exception, getHttpStatusFromException(exception.getClass()));
    }

    public HttpStatus getHttpStatusFromException(final Class<?> exception) {
        return EXCEPTION_STATUS.getOrDefault(
                exception,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<?> doHandle(final Exception exception, final HttpStatus status) {
        final String errorId = UUID.randomUUID().toString();
        log.error("Exception: ID={}, HttpStatus={}", errorId, status, exception);

        ErrorDetails errorDetails = new ErrorDetails(errorId, exception.getMessage());

        return ResponseEntity
                .status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorDetails);
    }
}
