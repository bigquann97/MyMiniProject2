package study.boardProject.common.exception;

public class LikeException extends RuntimeException {

    public static class AlreadyLikedException extends LikeException {
    }

    public static class LikeNotFoundException extends LikeException {
    }

}
