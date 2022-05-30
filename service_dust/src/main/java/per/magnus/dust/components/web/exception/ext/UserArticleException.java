package per.magnus.dust.components.web.exception.ext;

import per.magnus.dust.components.service.enums.ExceptionCodeEnum;
import per.magnus.dust.components.web.exception.DustException;

public class UserArticleException extends DustException {
    public UserArticleException(Integer code, String msg) {
        super(code, msg);
    }

    public static UserArticleException nullParameterException() {
        return new UserArticleException(ExceptionCodeEnum.ARGUMENT_RESOLVER_EXCEPTION.getCode(), "User or Article cannot be null!");
    }
}
