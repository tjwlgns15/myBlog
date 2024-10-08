package jihun.myBlog.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static jakarta.servlet.http.HttpServletResponse.*;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    MEMBER_NOT_FOUND(SC_NOT_FOUND, "존재하지 않는 회원입니다."),
    ALREADY_EXISTS_USERNAME(SC_CONFLICT, "이미 존재하는 아이디입니다."),
    ALREADY_EXISTS_NICKNAME(SC_CONFLICT, "이미 존재하는 닉네임입니다."),
    INVALID_TOKEN(SC_BAD_REQUEST, "유효하지 않은 토큰입니다."),
    TOKEN_EXPIRED(SC_BAD_REQUEST, "로그인 기간이 만료되었습니다. 다시 로그인 해주세요."),

    POST_NOT_FOUND(SC_NOT_FOUND, "게시글을 찾을 수 없습니다."),
    CATEGORY_NOT_FOUND(SC_NOT_FOUND, "카테고리를 찾을 수 없습니다."),
    USER_NOT_FOUND(SC_NOT_FOUND, "존재하지 않는 회원입니다."),
    COMMENT_NOT_FOUND(SC_NOT_FOUND, "존재하지 않는 댓글입니다."),
    CATEGORY_ALREADY_EXISTS(SC_NOT_FOUND, "사용중인 카테고리 이름입니다. 다른 이름을 사용해 주세요."),

    ;

    private final int code;
    private final String massage;
}
