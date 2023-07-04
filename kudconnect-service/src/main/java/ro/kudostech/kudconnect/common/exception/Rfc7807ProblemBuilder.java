package ro.kudostech.kudconnect.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;
import ro.kudostech.kudconnect.api.server.model.RFC7807ProblemDto;

import java.net.URI;
import java.time.OffsetDateTime;

public class Rfc7807ProblemBuilder {
  public static ResponseEntity<RFC7807ProblemDto> buildErrorResponse(
      Exception exception, HttpStatus httpStatus, WebRequest request) {
    RFC7807ProblemDto problem = new RFC7807ProblemDto();
    problem.setStatus(httpStatus.value());
    problem.setTitle(exception.getClass().getSimpleName());
    problem.setDetail(exception.getMessage());
    problem.setInstance(URI.create(request.getDescription(false)));
    problem.setTimestamp(OffsetDateTime.now());
    //TODO: add traceId

    return new ResponseEntity<>(problem, httpStatus);
  }
}
