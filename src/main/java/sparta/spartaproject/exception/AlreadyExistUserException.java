package sparta.spartaproject.exception;

public class AlreadyExistUserException extends RuntimeException {
    public AlreadyExistUserException() {
        super();
    }

    public AlreadyExistUserException(String message) {
        super(message);
    }

    public AlreadyExistUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
