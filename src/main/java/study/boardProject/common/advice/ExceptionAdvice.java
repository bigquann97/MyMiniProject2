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

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(AuthException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestApiException authException(AuthException e) {
        return new RestApiException(Status.AUTH_INVALID_TOKEN);
    }

    @ExceptionHandler(CommentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestApiException commentException(CommentException e) {
        return new RestApiException(Status.AUTH_INVALID_TOKEN);
    }

    @ExceptionHandler(LikeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestApiException likeException(LikeException e) {
        return new RestApiException(Status.AUTH_INVALID_TOKEN);
    }

    @ExceptionHandler(PostException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestApiException postException(PostException e) {
        return new RestApiException(Status.AUTH_INVALID_TOKEN);
    }


    @ExceptionHandler(TokenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestApiException tokenException(TokenException e) {
        return new RestApiException(Status.AUTH_INVALID_TOKEN);
    }

    // 커스텀 익셉션 외

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestApiException illegalArgumentException(Exception e) {
        log.error("e = {}", e.getMessage());
        return new RestApiException(Status.ILLEGAL_ARGUMENT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestApiException methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("e = {}", e.getMessage());
        return new RestApiException(Status.ILLEGAL_ARGUMENT);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestApiException httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("e = {}", e.getMessage());
        return new RestApiException(Status.GLOBAL_UNAUTHORIZED);
    }

}
