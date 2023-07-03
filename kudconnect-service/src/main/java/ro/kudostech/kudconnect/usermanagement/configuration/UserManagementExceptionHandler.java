package ro.kudostech.kudconnect.usermanagement.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ro.kudostech.kudconnect.api.server.model.RFC7807ProblemDto;
import ro.kudostech.kudconnect.usermanagement.domain.exception.CandidateAlreadyExistsException;

import static ro.kudostech.kudconnect.common.exception.Rfc7807ProblemBuilder.buildErrorResponse;

@Slf4j
@Order(Ordered.LOWEST_PRECEDENCE - 1)
@ControllerAdvice("ro.kudostech.kudconnect.usermanagement")
public class UserManagementExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CandidateAlreadyExistsException.class)
    public ResponseEntity<RFC7807ProblemDto> handleAllUncaughtExceptions(
            Exception ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        return buildErrorResponse(ex, HttpStatus.CONFLICT, request);
    }
}
