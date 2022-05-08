package per.magnus.dust.components.web.exception;

public class DustException extends Exception {
    Integer code;
    String message;

    public DustException(Integer code, String msg) {
        this.code = code;
        this.message = msg;
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
