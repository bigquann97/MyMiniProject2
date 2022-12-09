package sparta.spartaproject.exception;

public class WrongPwException extends RuntimeException {
    public WrongPwException() {
        super();
    }

    public WrongPwException(String message) {
        super(message);
    }

    public WrongPwException(String message, Throwable cause) {
        super(message, cause);
    }
}
