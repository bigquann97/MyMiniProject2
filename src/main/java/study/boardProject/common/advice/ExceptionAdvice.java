package study.boardProject.common.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import study.boardProject.common.exception.*;
import study.boardProject.common.exception.api.RestApiException;
import study.boardProject.common.exception.api.Status;

import static study.boardProject.common.exception.AuthException.*;
import static study.boardProject.common.exception.CommentException.*;
import static study.boardProject.common.exception.TokenException.*;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(AuthException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestApiException authException(AuthException e) {
        RestApiException result = new RestApiException();

        if (e instanceof DuplicatedLoginIdException) {
            return result.changeStatus(Status.AUTH_DUPLICATED_ID);
        } else if (e instanceof PasswordNotCorrectException) {
            return result.changeStatus(Status.AUTH_PASSWORD_NOT_CORRECT);
        } else if (e instanceof UserNotFoundException) {
            return result.changeStatus(Status.AUTH_USER_NOT_FOUND);
        } else if (e instanceof AuthenticationException) {
            return result.changeStatus(Status.AUTH_AUTHENTICATION);
        } else if (e instanceof DuplicatedNicknameException) {
            return result.changeStatus(Status.AUTH_DUPLICATED_NICKNAME);
        } else if (e instanceof DuplicatedEmailException) {
            return result.changeStatus(Status.AUTH_DUPLICATED_EMAIL);
        }

        return result.changeStatus(Status.AUTH);
    }

    @ExceptionHandler(CommentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestApiException commentException(CommentException e) {
        RestApiException result = new RestApiException();

        if (e instanceof CommentNotFoundException) {
            result = new RestApiException(Status.COMMENT_NOT_FOUND);
        }

        return result.changeStatus(Status.COMMENT);
    }

    @ExceptionHandler(LikeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestApiException likeException(LikeException e) {
        RestApiException result = new RestApiException();

        if (e instanceof LikeException.AlreadyLikedException) {
            return result.changeStatus(Status.LIKE_ALREADY_LIKED);
        } else if (e instanceof LikeException.LikeNotFoundException) {
            return result.changeStatus(Status.LIKE_NOT_FOUNT);
        }

        return result.changeStatus(Status.LIKE);
    }

    @ExceptionHandler(PostException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestApiException postException(PostException e) {
        RestApiException result = new RestApiException();

        if (e instanceof PostException.PostNotFoundException) {
            return result.changeStatus(Status.POST_NOT_FOUND);
        }

        return result.changeStatus(Status.POST);
    }


    @ExceptionHandler(TokenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestApiException tokenException(TokenException e) {
        RestApiException result = new RestApiException();

        if (e instanceof AlreadyLogoutException) {
            return result.changeStatus(Status.TOKEN_ALREADY_LOGOUT);
        } else if (e instanceof InvalidRefreshTokenException) {
            return result.changeStatus(Status.TOKEN_INVALID_REFRESH_TOKEN);
        } else if (e instanceof TokenOwnerNotMatchException) {
            return result.changeStatus(Status.TOKEN_OWNER_NOT_MATCH);
        }

        return result.changeStatus(Status.TOKEN);
    }

    // 커스텀 익셉션 외

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestApiException illegalArgumentException(IllegalArgumentException e) {
        RestApiException result = new RestApiException();
        log.error("e = {}", e.getMessage());
        return result.changeStatus(Status.ILLEGAL_ARGUMENT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestApiException methodArgumentNotValidException(MethodArgumentNotValidException e) {
        RestApiException result = new RestApiException();
        log.error("e = {}", e.getMessage());
        return result.changeStatus(Status.METHOD_ARGUMENT_NOT_VALID);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestApiException httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        RestApiException result = new RestApiException();
        log.error("e = {}", e.getMessage());
        return result.changeStatus(Status.HTTP_REQUEST_METHOD_NOT_SUPPORTED);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public RestApiException exception(Exception e) {
        RestApiException result = new RestApiException();
        log.error("e = {}", e.getMessage());
        return result.changeStatus(Status.NOT_EXPECTED_EXCEPTION);
    }
}
