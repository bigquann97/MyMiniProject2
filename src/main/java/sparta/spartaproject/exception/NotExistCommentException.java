package sparta.spartaproject.exception;

public class NotExistCommentException extends RuntimeException {
    public NotExistCommentException() {
        super();
    }

    public NotExistCommentException(String message) {
        super(message);
    }

    public NotExistCommentException(String message, Throwable cause) {
        super(message, cause);
    }
}
