package sparta.spartaproject.exception;

public class MismatchException extends RuntimeException {

    public MismatchException() {
        super();
    }

    public MismatchException(String message) {
        super(message);
    }

    public MismatchException(String message, Throwable cause) {
        super(message, cause);
    }

}
