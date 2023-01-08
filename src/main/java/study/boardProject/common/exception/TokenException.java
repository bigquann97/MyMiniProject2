package study.boardProject.common.exception;

public class TokenException extends RuntimeException{

    public static class AlreadyLogoutException extends TokenException {
    }

    public static class InvalidRefreshTokenException extends TokenException {
    }

    public static class TokenOwnerNotMatchException extends TokenException {
    }

}
