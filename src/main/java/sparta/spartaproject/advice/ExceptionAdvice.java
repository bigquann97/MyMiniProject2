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
import sparta.spartaproject.service.result.ResultService;
import sparta.spartaproject.result.Result;
import sparta.spartaproject.result.Status;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionAdvice {

    private final ResultService resultService;

    @ExceptionHandler(AdminKeyNotMatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result adminKeyNotMatchException(AdminKeyNotMatchException e) {
        log.error("e = {}", e.getMessage());
        return resultService.getFailureResult(Status.F_ADMIN_KEY_NOT_MATCH);
    }

    @ExceptionHandler(AlreadyExistUserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result alreadyExistUserException(AlreadyExistUserException e) {
        log.error("e = {}", e.getMessage());
        return resultService.getFailureResult(Status.F_USER_ALREADY_EXIST);
    }

    @ExceptionHandler(CommentPostNotMathException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result commentPostNotMathException(CommentPostNotMathException e) {
        log.error("e = {}", e.getMessage());
        return resultService.getFailureResult(Status.F_COMMENT_POST_NOT_MATCH);
    }

    @ExceptionHandler(InvalidTokenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result invalidTokenException(InvalidTokenException e) {
        log.error("e = {}", e.getMessage());
        return resultService.getFailureResult(Status.F_INVALID_TOKEN);
    }

    @ExceptionHandler(NotExistCommentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result notExistCommentException(NotExistCommentException e) {
        log.error("e = {}", e.getMessage());
        return resultService.getFailureResult(Status.F_COMMENT_NOT_EXIST);
    }

    @ExceptionHandler(NotExistPostException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result notExistPostException(NotExistPostException e) {
        log.error("e = {}", e.getMessage());
        return resultService.getFailureResult(Status.F_POST_NOT_EXIST);
    }

    @ExceptionHandler(NotExistUserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result notExistUserException(NotExistUserException e) {
        log.error("e = {}", e.getMessage());
        return resultService.getFailureResult(Status.F_USER_NOT_EXIST);
    }

    @ExceptionHandler(PwNotMatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result pwNotMatchException(PwNotMatchException e) {
        log.error("e = {}", e.getMessage());
        return resultService.getFailureResult(Status.F_USER_PW_NOT_MATCH);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result UnauthorizedException(UnauthorizedException e) {
        log.error("e = {}", e.getMessage());
        return resultService.getFailureResult(Status.F_UNAUTHORIZED);
    }

    @ExceptionHandler(WrongPwException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result wrongPwException(WrongPwException e) {
        log.error("e = {}", e.getMessage());
        return resultService.getFailureResult(Status.F_USER_WRONG_PW);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result illegalArgumentException(Exception e) {
        log.error("e = {}", e.getMessage());
        return resultService.getFailureResult(Status.F_ILLEGAL_ARGUMENT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("e = {}", e.getMessage());
        return resultService.getFailureResult(Status.F_ILLEGAL_ARGUMENT);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("e = {}", e.getMessage());
        return resultService.getFailureResult(Status.F_UNAUTHORIZED);
    }

}
