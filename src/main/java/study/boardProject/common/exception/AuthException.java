package study.boardProject.common.exception;

public class AuthException extends RuntimeException {

    public static class DuplicatedLoginIdException extends AuthException {
    }

    public static class PasswordNotCorrectException extends AuthException {
    }

    public static class UserNotFoundException extends AuthException {
    }

    public static class AuthenticationException extends AuthException {
    }

    public static class DuplicatedNicknameException extends AuthException {
    }

    public static class DuplicatedEmailException extends AuthException {
    }

}
