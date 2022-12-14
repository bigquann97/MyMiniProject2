package sparta.spartaproject.service.result;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.spartaproject.dto.post.PostDto;
import sparta.spartaproject.result.Result;
import sparta.spartaproject.result.DataResult;
import sparta.spartaproject.result.Status;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ResultService {

    public Result getSuccessResult(Status status) {
        return Result.baseBuilder()
                .success(true)
                .message(status.getMsg())
                .statusCode(status.getCode())
                .build();
    }

    public Result getFailureResult(Status status) {
        return Result.baseBuilder()
                .success(false)
                .message(status.getMsg())
                .statusCode(status.getCode())
                .build();
    }

    public <T> Result getSuccessDataResult(Status status, T data) {
        return DataResult.builder()
                .success(true)
                .message(status.getMsg())
                .statusCode(status.getCode())
                .data(data)
                .build();
    }
}