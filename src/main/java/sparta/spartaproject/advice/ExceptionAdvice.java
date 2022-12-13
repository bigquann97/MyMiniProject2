package sparta.spartaproject.advice;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sparta.spartaproject.exception.*;
import sparta.spartaproject.service.result.ResultService;
import sparta.spartaproject.result.Result;
import sparta.spartaproject.result.Status;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionAdvice {

    private final ResultService resultService;

    @ExceptionHandler(AlreadyExistUserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result alreadyExistUserException() {
        return resultService.getFailureResult(Status.F_USER_ALREADY_EXIST.getCode(), Status.F_USER_ALREADY_EXIST.getMsg());
    }

    @ExceptionHandler(WrongPwException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result wrongPwException() {
        return resultService.getFailureResult(Status.F_USER_WRONG_PW.getCode(), Status.F_USER_WRONG_PW.getMsg());
    }

    @ExceptionHandler(NotExistUserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result notExistUserException() {
        return resultService.getFailureResult(Status.F_USER_NOT_EXIST.getCode(), Status.F_USER_NOT_EXIST.getMsg());
    }

    @ExceptionHandler(NotExistPostException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result notExistPostException() {
        return resultService.getFailureResult(Status.F_POST_NOT_EXIST.getCode(), Status.F_POST_NOT_EXIST.getMsg());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result illegalArgumentException(Exception e) {
        return resultService.getFailureResult(Status.F_ILLEGAL_ARGUMENT.getCode(), e.getMessage());
    }

    @ExceptionHandler(InvalidTokenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result invalidTokenException() {
        return resultService.getFailureResult(Status.F_INVALID_TOKEN.getCode(), Status.F_INVALID_TOKEN.getMsg());
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result UnauthorizedException() {
        return resultService.getFailureResult(Status.F_UNAUTHORIZED.getCode(), Status.F_UNAUTHORIZED.getMsg());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result methodArgumentNotValidException(MethodArgumentNotValidException e) {
        return resultService.getFailureResult(Status.F_ILLEGAL_ARGUMENT.getCode(), Status.F_ILLEGAL_ARGUMENT.getMsg());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result httpRequestMethodNotSupportedException() {
        return resultService.getFailureResult(Status.F_UNAUTHORIZED.getCode(), Status.F_UNAUTHORIZED.getMsg());
    }

    @ExceptionHandler(AdminKeyNotMatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result adminKeyNotMatchException() {
        return resultService.getFailureResult(Status.F_ADMIN_KEY_NOT_MATCH.getCode(), Status.F_ADMIN_KEY_NOT_MATCH.getMsg());
    }

    @ExceptionHandler(NotExistCommentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result notExistCommentException() {
        return resultService.getFailureResult(Status.F_COMMENT_NOT_EXIST.getCode(), Status.F_COMMENT_NOT_EXIST.getMsg());
    }
    
}
