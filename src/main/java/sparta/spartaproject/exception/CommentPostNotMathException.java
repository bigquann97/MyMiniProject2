package sparta.spartaproject.exception;

public class CommentPostNotMathException extends RuntimeException {
    public CommentPostNotMathException() {
        super();
    }

    public CommentPostNotMathException(String message) {
        super(message);
    }

    public CommentPostNotMathException(String message, Throwable cause) {
        super(message, cause);
    }
}
