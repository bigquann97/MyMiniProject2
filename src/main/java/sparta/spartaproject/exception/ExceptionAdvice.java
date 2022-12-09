package sparta.spartaproject.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sparta.spartaproject.service.ResponseService;
import sparta.spartaproject.service.result.Result;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionAdvice {

    private final ResponseService responseService;

    @ExceptionHandler(AlreadyExistUserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result alreadyExistUserException() {
        return responseService.getFailureResult(-101, "중복 유저 존재");
    }

    @ExceptionHandler(InvalidPwException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result invalidPwException() {
        return responseService.getFailureResult(-102, "비밀번호 불일치");
    }

    @ExceptionHandler(NotExistUserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result notExistUserException() {
        return responseService.getFailureResult(-103, "존재하지 않는 유저");
    }


    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result illegalArgumentException() {
        return responseService.getFailureResult(-100, "잘못된 입력");
    }

}
