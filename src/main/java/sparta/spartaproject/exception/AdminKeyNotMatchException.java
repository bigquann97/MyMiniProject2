package sparta.spartaproject.exception;

public class AdminKeyNotMatchException extends RuntimeException {
    public AdminKeyNotMatchException() {
        super();
    }

    public AdminKeyNotMatchException(String message) {
        super(message);
    }

    public AdminKeyNotMatchException(String message, Throwable cause) {
        super(message, cause);
    }
}
