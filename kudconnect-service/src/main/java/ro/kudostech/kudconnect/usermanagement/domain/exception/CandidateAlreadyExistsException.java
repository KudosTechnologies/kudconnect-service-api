package ro.kudostech.kudconnect.usermanagement.domain.exception;

public class CandidateAlreadyExistsException extends RuntimeException{
    public CandidateAlreadyExistsException(String message) {
        super(message);
    }
}
