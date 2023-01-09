package study.boardProject.common.exception.api;

import lombok.Getter;

@Getter
public class RestApiException {

    private int code;
    private String errorMessage;

    public RestApiException(Status status) {
        this.code = status.getCode();
        this.errorMessage = status.getMsg();
    }

    public RestApiException() {
        this.code = 444;
        this.errorMessage = "잘못된 접근입니다.";
    }

    public RestApiException changeStatus(Status status) {
        this.code = status.getCode();
        this.errorMessage = status.getMsg();
        return this;
    }

}
