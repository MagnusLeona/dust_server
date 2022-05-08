package per.magnus.dust.components.web.resolver;

import com.alibaba.fastjson.JSON;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import per.magnus.dust.business.domain.User;
import per.magnus.dust.components.repos.redis.RedisService;
import per.magnus.dust.components.service.dict.CookieNameDict;
import per.magnus.dust.components.service.enums.SessionAttributeEnum;
import per.magnus.dust.components.service.utils.WebUtils;
import per.magnus.dust.components.web.exception.ext.ArgumentResolverException;
import per.magnus.dust.components.web.exception.ext.AuthCheckException;
import per.magnus.dust.components.web.resolver.annotation.InjectUser;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@Component
public class DustUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Resource
    RedisService redisService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(InjectUser.class) && parameter.getParameterType().isAssignableFrom(User.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // 从Session中解析
        HttpServletRequest nativeRequest = (HttpServletRequest) webRequest.getNativeRequest();
        Cookie cookieByName = WebUtils.getCookieByName(CookieNameDict.COOKIE_LOGIN_TOKEN, nativeRequest);
        if (Objects.isNull(cookieByName) || !redisService.exist(cookieByName.getValue())) {
            throw AuthCheckException.defaultAuthCheckException();
        }
        return JSON.parseObject(redisService.get(cookieByName.getValue()), User.class);
    }
}
