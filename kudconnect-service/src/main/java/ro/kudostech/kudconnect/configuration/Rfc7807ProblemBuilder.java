package ro.kudostech.kudconnect.configuration;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;
import ro.kudostech.kudconnect.api.server.model.RFC7807ProblemDto;
import ro.kudostech.kudconnect.api.server.model.ViolationDto;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;

public class Rfc7807ProblemBuilder {
  public static ResponseEntity<RFC7807ProblemDto> buildErrorResponse(
          Exception exception, HttpStatus httpStatus, WebRequest request, List<ViolationDto> violations) {
    RFC7807ProblemDto problem = new RFC7807ProblemDto();
    problem.setStatus(httpStatus.value());
    problem.setTitle(httpStatus.name());
    problem.setDetail(exception.getMessage());
    problem.setInstance(URI.create(request.getDescription(false)));
    problem.setTimestamp(OffsetDateTime.now());
    problem.setViolations(violations);
    //TODO: add traceId

    return new ResponseEntity<>(problem, httpStatus);
  }
}
