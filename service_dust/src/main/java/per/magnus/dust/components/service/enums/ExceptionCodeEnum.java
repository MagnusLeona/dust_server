package per.magnus.dust.components.service.enums;

public enum ExceptionCodeEnum {

    ARGUMENT_RESOLVER_EXCEPTION(0),
    AUTHCHECK_EXCEPTION(1);

    int code;

    ExceptionCodeEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
