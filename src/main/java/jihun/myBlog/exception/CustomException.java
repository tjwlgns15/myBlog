package jihun.myBlog.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final int statusCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMassage());
        this.statusCode = errorCode.getCode();
    }
}
