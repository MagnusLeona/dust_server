package per.magnus.dust.components.web.exception.ext;

import per.magnus.dust.components.service.dict.ExceptionMessageDict;
import per.magnus.dust.components.service.enums.ExceptionCodeEnum;
import per.magnus.dust.components.web.exception.DustException;

public class ArgumentResolverException extends DustException {

    public ArgumentResolverException(Integer code, String s) {
        super(code, s);
    }

    public static DustException defaultArgsResolverException() {
        return new ArgumentResolverException(ExceptionCodeEnum.ARGUMENT_RESOLVER_EXCEPTION.getCode(), ExceptionMessageDict.ARGUMENT_RESOLVER_ERROR);
    }
}
