package sparta.spartaproject.advice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sparta.spartaproject.exception.*;
import sparta.spartaproject.exception.api.RestApiException;
import sparta.spartaproject.exception.api.Status;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionAdvice {

    @ExceptionHandler(MismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestApiException adminKeyNotMatchException(MismatchException e) {
        log.error("e = {}", e.getMessage());
        return new RestApiException(Status.F_ADMIN_KEY_NOT_MATCH);
    }

    @ExceptionHandler(TokenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestApiException invalidTokenException(TokenException e) {
        log.error("e = {}", e.getMessage());
        return new RestApiException(Status.F_INVALID_TOKEN);
    }

    @ExceptionHandler(SignException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestApiException pwNotMatchException(SignException e) {
        log.error("e = {}", e.getMessage());
        return new RestApiException(Status.F_USER_PW_NOT_MATCH);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestApiException illegalArgumentException(Exception e) {
        log.error("e = {}", e.getMessage());
        return new RestApiException(Status.F_ILLEGAL_ARGUMENT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestApiException methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("e = {}", e.getMessage());
        return new RestApiException(Status.F_ILLEGAL_ARGUMENT);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestApiException httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("e = {}", e.getMessage());
        return new RestApiException(Status.F_UNAUTHORIZED);
    }

}
