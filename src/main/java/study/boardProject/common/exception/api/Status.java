package study.boardProject.common.exception.api;

import lombok.Getter;

@Getter
public enum Status {

    AUTH(400, "잘못된 인증 접근입니다."),
    AUTH_DUPLICATED_ID(400, "이미 가입된 아이디입니다."),
    AUTH_PASSWORD_NOT_CORRECT(400, "잘못된 비밀번호입니다."),
    AUTH_USER_NOT_FOUND(400, "존재하지 않는 유저입니다."),
    AUTH_AUTHENTICATION(400, "해당 권한이 없습니다."),
    AUTH_DUPLICATED_EMAIL(400, "이미 가입된 이메일 입니다."),
    AUTH_DUPLICATED_NICKNAME(400, "이미 존재하는 별명입니다."),

    COMMENT(400, "잘못된 댓글 접근입니다."),
    COMMENT_NOT_FOUND(400, "존재하지 않는 댓글입니다."),
    COMMENT_CANNOT_REPLY(400, "대댓글을 달 수 없습니다."),

    LIKE(400, "잘못된 좋아요 접근입니다."),
    LIKE_ALREADY_LIKED(400, "이미 좋아요가 존재합니다."),
    LIKE_NOT_FOUNT(400, "좋아요 이력이 없습니다."),

    POST(400, "잘못된 게시글 접근입니다."),
    POST_NOT_FOUND(400, "존재하지 않는 게시물입니다."),

    TOKEN(400, "잘못된 토큰 접근입니다."),
    TOKEN_ALREADY_LOGOUT(400, "이미 로그아웃한 사용자입니다."),
    TOKEN_INVALID_REFRESH_TOKEN(400, "유효하지 않은 리프레시토큰입니다."),
    TOKEN_OWNER_NOT_MATCH(400, "토큰 소유자가 일치하지 않습니다."),

    ILLEGAL_ARGUMENT(400, "잘못된 입력입니다."),
    METHOD_ARGUMENT_NOT_VALID(400, "잘못된 접근 메서드입니다.."),
    HTTP_REQUEST_METHOD_NOT_SUPPORTED(400, "잘못된 HTTP 메서드 접근입니다."),
    DB_INTEGRATION(444, "무결성 위반(동시성 문제)"),

    NOT_EXPECTED_EXCEPTION(444, "예외 사항을 서버에 보고해주세요.");

    Status (int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private final int code;
    private final String msg;

}
