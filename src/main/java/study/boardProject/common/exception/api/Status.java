package study.boardProject.common.exception.api;

import lombok.Getter;

@Getter
public enum Status {

    AUTH_PASSWORD_NOT_MATCH(400, "비밀번호가 재입력 비밀번호와 일치하지 않습니다."),
    AUTH_ALREADY_EXIST_USER(400, "중복된 아이디의 유저가 존재합니다."),
    AUTH_WRONG_PASSWORD(400, "잘못된 비밀번호입니다."),
    AUTH_NOT_EXIST(400, "존재하지 않는 유저입니다."),
    AUTH_ADMIN_KEY_NOT_MATCH(400, "어드민 생성 키 오류입니다."),

    TOKEN_OWNER_NOT_MATCH(400, "토큰 소유자가 일치하지 않습니다."),
    TOKEN_INVALID_REFRESH_TOKEN(400, "유효하지 않은 리프레시토큰입니다."),

    COMMENT_NOT_EXIST(400, "존재하지 않는 댓글입니다."),
    COMMENT_POST_NOT_MATCH(400, "해당 댓글을 가진 게시물이 아닙니다."),

    POST_NOT_EXIST(400, "존재하지 않는 게시물입니다."),

    ILLEGAL_ARGUMENT(400, "잘못된 입력입니다."),
    AUTH_INVALID_TOKEN(401, "잘못된 토큰입니다."),
    GLOBAL_UNAUTHORIZED(402, "잘못된 접근입니다.");

    Status (int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private final int code;
    private final String msg;

}