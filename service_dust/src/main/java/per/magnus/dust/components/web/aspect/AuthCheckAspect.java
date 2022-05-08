package per.magnus.dust.components.web.aspect;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import per.magnus.dust.business.domain.User;
import per.magnus.dust.components.repos.redis.RedisService;
import per.magnus.dust.components.service.constant.SessionAttributeConstant;
import per.magnus.dust.components.service.dict.CookieNameDict;
import per.magnus.dust.components.service.enums.SessionAttributeEnum;
import per.magnus.dust.components.service.utils.WebUtils;
import per.magnus.dust.components.web.exception.ext.AuthCheckException;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@Slf4j
@SuppressWarnings("unused")
public class AuthCheckAspect {

    @Resource
    RedisService redisService;

    @Pointcut("@annotation(per.magnus.dust.components.web.aspect.annotation.AuthCheckRequired)")
    void pointCut() {
    }

    /*
    check before these controllers requiring login status
     */
    @Before("pointCut()")
    void beforeExecute() throws Throwable {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        Cookie cookie = WebUtils.getCookieByName(CookieNameDict.COOKIE_LOGIN_TOKEN, requestAttributes.getRequest());
        if (Objects.isNull(cookie)) {
            throw AuthCheckException.defaultAuthCheckException();
        }
        String cookieName = cookie.getName(); // 判断此cookie是否有效
        if (!redisService.exist(cookieName)) {
            throw AuthCheckException.defaultAuthCheckException();
        }
        redisService.flushKey(cookieName, SessionAttributeConstant.SESSION_MAX_INACTIVE_TIME);
        String tokenId = redisService.get(cookieName);
        User loginUser = JSON.parseObject(tokenId, User.class);
        // 如果是登录状态，需要给日志的MDC空间插入user信息
        MDC.put("user", String.format("[user: %s]", loginUser.getId()));
    }
}
