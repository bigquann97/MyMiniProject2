package sparta.spartaproject.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sparta.spartaproject.service.ResultService;
import sparta.spartaproject.service.result.Result;
import sparta.spartaproject.service.result.Status;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionAdvice {

    private final ResultService resultService;

    @ExceptionHandler(AlreadyExistUserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result alreadyExistUserException() {
        return resultService.getFailureResult(Status.F_ALREADY_EXIST_USER.getCode(), Status.F_ALREADY_EXIST_USER.getMsg());
    }

    @ExceptionHandler(WrongPwException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result wrongPwException() {
        return resultService.getFailureResult(Status.F_WRONG_PW.getCode(), Status.F_WRONG_PW.getMsg());
    }

    @ExceptionHandler(NotExistUserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result notExistUserException() {
        return resultService.getFailureResult(Status.F_NOT_EXIST_USER.getCode(), Status.F_NOT_EXIST_USER.getMsg());
    }

    @ExceptionHandler(NotExistPostException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result notExistPostException() {
        return resultService.getFailureResult(Status.F_NOT_EXIST_POST.getCode(), Status.F_NOT_EXIST_POST.getMsg());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result illegalArgumentException() {
        return resultService.getFailureResult(Status.F_ILLEGAL_ARGUMENT.getCode(), Status.F_ILLEGAL_ARGUMENT.getMsg());
    }

    @ExceptionHandler(InvalidTokenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result invalidTokenException() {
        return resultService.getFailureResult(Status.F_INVALID_TOKEN.getCode(), Status.F_INVALID_TOKEN.getMsg());
    }

    @ExceptionHandler(UnautorizedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result UnauthorizedException() {
        return resultService.getFailureResult(Status.F_UNAUTHORIZED.getCode(), Status.F_UNAUTHORIZED.getMsg());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result methodArgumentNotValidException() {
        return resultService.getFailureResult(Status.F_ILLEGAL_ARGUMENT.getCode(), Status.F_ILLEGAL_ARGUMENT.getMsg());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result httpRequestMethodNotSupportedException() {
        return resultService.getFailureResult(Status.F_UNAUTHORIZED.getCode(), Status.F_UNAUTHORIZED.getMsg());
    }

}
