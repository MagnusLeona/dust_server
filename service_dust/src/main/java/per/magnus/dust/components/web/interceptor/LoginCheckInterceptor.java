package per.magnus.dust.components.web.interceptor;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import per.magnus.dust.business.domain.User;
import per.magnus.dust.components.repos.redis.RedisService;
import per.magnus.dust.components.service.constant.SessionAttributeConstant;
import per.magnus.dust.components.service.dict.CookieNameDict;
import per.magnus.dust.components.service.utils.WebUtils;
import per.magnus.dust.components.web.aspect.annotation.LoginCheckRequired;
import per.magnus.dust.components.web.exception.DustException;
import per.magnus.dust.components.web.exception.ext.AuthCheckException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Slf4j
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Autowired
    RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws DustException {
        // 校验是否需要登录，校验登录状态
        if (handler instanceof HandlerMethod handlerMethod) {
            // 如果是HandlerMethod，则判断是否有相应的注解
            if (!(Objects.isNull(handlerMethod.getMethod().getAnnotation(LoginCheckRequired.class)) && Objects.isNull(handlerMethod.getBeanType().getAnnotation(LoginCheckRequired.class)))) {
                Cookie cookie = WebUtils.getCookieByName(CookieNameDict.COOKIE_LOGIN_TOKEN, request);
                if (Objects.isNull(cookie)) {
                    throw AuthCheckException.defaultAuthCheckException();
                }
                String cookieValue = cookie.getValue(); // 判断此cookie是否有效
                if (!redisService.exist(cookieValue)) {
                    throw AuthCheckException.defaultAuthCheckException();
                }
                redisService.flushKey(cookieValue, SessionAttributeConstant.SESSION_MAX_INACTIVE_TIME);
                String tokenId = redisService.get(cookieValue);
                User loginUser = JSON.parseObject(tokenId, User.class);
                // 如果是登录状态，需要给日志的MDC空间插入user信息
                MDC.put("user", String.format("[user: %s]", loginUser.getId()));
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
