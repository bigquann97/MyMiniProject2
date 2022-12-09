package sparta.spartaproject.exception;

public class InvalidPwException extends RuntimeException {
    public InvalidPwException() {
        super();
    }

    public InvalidPwException(String message) {
        super(message);
    }

    public InvalidPwException(String message, Throwable cause) {
        super(message, cause);
    }
}
