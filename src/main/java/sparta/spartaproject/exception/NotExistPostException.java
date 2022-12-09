package sparta.spartaproject.exception;

public class NotExistPostException extends RuntimeException {
    public NotExistPostException() {
        super();
    }

    public NotExistPostException(String message) {
        super(message);
    }

    public NotExistPostException(String message, Throwable cause) {
        super(message, cause);
    }
}
