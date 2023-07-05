package ro.kudostech.kudconnect.common.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import lombok.experimental.UtilityClass;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;

@UtilityClass
public class PatchViolation {

    private static Path pathFromString(final String nodeName) {
        final PathImpl path = PathImpl.createRootPath();
        path.addPropertyNode(nodeName);
        return path;
    }

    public static <T> ConstraintViolation<T> forIllegalOperation(final String interpolatedMessage, final Class<T> clazz, final String path) {

        return ConstraintViolationImpl.forParameterValidation(
                null,
                null,
                null,
                interpolatedMessage,
                clazz,
                null,
                null,
                null,
                pathFromString(path),
                null,
                null,
                null
        );
    }

    public static <T> ConstraintViolation<T> forIllegalArgument(final String interpolatedMessage, final Class<T> clazz, final String path) {

        return ConstraintViolationImpl.forParameterValidation(
                null,
                null,
                null,
                interpolatedMessage,
                clazz,
                null,
                null,
                null,
                pathFromString(path),
                null,
                null,
                null
        );
    }
}
