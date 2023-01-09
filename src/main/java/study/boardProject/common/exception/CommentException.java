package study.boardProject.common.exception;

public class CommentException extends RuntimeException {

    public static class CommentNotFoundException extends CommentException {
    }

    public static class CannotReplyException extends CommentException {
    }

}
