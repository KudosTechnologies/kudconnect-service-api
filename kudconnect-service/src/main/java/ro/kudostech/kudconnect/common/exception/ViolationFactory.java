package ro.kudostech.kudconnect.common.exception;

import jakarta.validation.ConstraintViolation;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import ro.kudostech.kudconnect.api.server.model.ViolationDto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ViolationFactory {
    public List<ViolationDto> createViolations(Set<ConstraintViolation<?>> violations) {
        return violations.stream()
                .map(this::createViolation)
                .collect(Collectors.toList());
    }

    public List<ViolationDto> createViolations(BindingResult result) {
        final Stream<ViolationDto> fieldErrors = result.getFieldErrors().stream().map(this::createViolation);
        final Stream<ViolationDto> globalErrors = result.getGlobalErrors().stream().map(this::createViolation);
        return Stream.concat(fieldErrors, globalErrors).collect(Collectors.toList());
    }

    private ViolationDto createViolation(ConstraintViolation<?> constraintViolation) {
        final String fieldName = constraintViolation.getPropertyPath().toString();
        return new ViolationDto().field(fieldName).message(constraintViolation.getMessage());
    }

    private ViolationDto createViolation(FieldError error) {
        final String fieldName = error.getField();
        return new ViolationDto().field(fieldName).message(error.getDefaultMessage());
    }

    private ViolationDto createViolation(ObjectError error) {
        final String fieldName = error.getObjectName();
        return new ViolationDto().field(fieldName).message(error.getDefaultMessage());
    }
}
