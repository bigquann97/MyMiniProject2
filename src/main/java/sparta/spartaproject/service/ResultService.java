package sparta.spartaproject.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.spartaproject.service.result.Result;
import sparta.spartaproject.service.result.DataResult;

@Service
@Transactional(readOnly = true)
public class ResultService {

    public <T> Result getSuccessDataResult(int code, String msg, T data) {
        return DataResult.builder()
                .success(true)
                .message(msg)
                .statusCode(code)
                .data(data)
                .build();
    }

    public Result getFailureResult(int code, String msg) {
        return Result.baseBuilder()
                .success(false)
                .message(msg)
                .statusCode(code)
                .build();
    }

    public Result getSuccessResult(int code, String msg) {
        return Result.baseBuilder()
                .success(true)
                .message(msg)
                .statusCode(code)
                .build();
    }
}