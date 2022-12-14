package sparta.spartaproject.exception;

public class PwNotMatchException extends RuntimeException {
    public PwNotMatchException() {
        super();
    }

    public PwNotMatchException(String message) {
        super(message);
    }

    public PwNotMatchException(String message, Throwable cause) {
        super(message, cause);
    }
}
