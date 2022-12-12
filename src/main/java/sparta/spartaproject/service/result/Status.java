package sparta.spartaproject.service.result;

import lombok.Getter;

@Getter
public enum Status {
    S_USER_CREATED(100, "유저가 정상 생성되었습니다."),
    S_USER_LOGIN(101, "정상 로그인되었습니다."),

    S_POST_VIEW(110, "게시글을 정상적으로 조회했습니다."),
    S_POST_UPLOAD(111, "게시글을 정상적으로 작성했습니다."),
    S_POST_MODIFY(112, "게시글을 정상적으로 수정했습니다."),
    S_POST_DELETE(113, "게시글을 정상적으로 삭제했습니다."),

    F_USER_INVALID_PW(300, "비밀번호가 재입력 비밀번호와 일치하지 않습니다."),
    F_USER_ALREADY_EXIST(301, "중복된 아이디의 유저가 존재합니다."),
    F_USER_WRONG_PW(302, "잘못된 비밀번호입니다."),
    F_USER_NOT_EXIST(303, "존재하지 않는 유저입니다."),

    F_POST_NOT_EXIST(310, "존재하지 않는 게시물입니다."),

    F_ILLEGAL_ARGUMENT(400, "잘못된 입력입니다."),
    F_INVALID_TOKEN(401, "잘못된 토큰입니다."),
    F_UNAUTHORIZED(402, "잘못된 접근입니다."),
    ;

    Status (int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private final int code;
    private final String msg;
}


/*
    this.code = code;

    private String code;

    public static Status valueOfCode(String errorCode) {
        return Arrays.stream(values())
                .filter(value -> value.code.equals(errorCode))
                .findAny()
                .orElse(null);
    }
 */