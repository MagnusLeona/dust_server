package per.magnus.dust.components.web.exception.ext;

import per.magnus.dust.components.service.dict.ExceptionMessageDict;
import per.magnus.dust.components.service.enums.ResponseStatusEnum;
import per.magnus.dust.components.web.exception.DustException;

public class AuthCheckException extends DustException {

    public AuthCheckException(Integer code, String s) {
        super(code, s);
    }

    public static DustException defaultAuthCheckException() {
        return new AuthCheckException(ResponseStatusEnum.STATUS_LOGINREQUIRED.code(), ExceptionMessageDict.LOGIN_FAILED);
    }

    public static DustException unknownUserException() {
        return new AuthCheckException(ResponseStatusEnum.STATUS_LOGININFOERROR.code(), ExceptionMessageDict.UNKNOWN_USER);
    }

    public static DustException accessDeniedForLowAuth() {
        return new AuthCheckException(ResponseStatusEnum.AUTH_CHECK_DENIED.code(), ExceptionMessageDict.AUTH_DENIED);
    }
}
