package ro.kudostech.kudconnect.configuration;

import static ro.kudostech.kudconnect.common.exception.Rfc7807ProblemBuilder.buildErrorResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ro.kudostech.kudconnect.api.server.model.RFC7807ProblemDto;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<RFC7807ProblemDto> handleAllUncaughtExceptions(
      Exception ex, WebRequest request) {
    log.error(ex.getMessage(), ex);
    return buildErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, request);
  }
}
