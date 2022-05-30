package per.magnus.dust.business.service;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import per.magnus.dust.business.domain.User;
import per.magnus.dust.business.mapper.UserMapper;
import per.magnus.dust.components.repos.redis.RedisService;
import per.magnus.dust.components.service.constant.SessionAttributeConstant;
import per.magnus.dust.components.service.dict.CookieNameDict;
import per.magnus.dust.components.service.enums.SessionAttributeEnum;
import per.magnus.dust.components.web.exception.DustException;
import per.magnus.dust.components.web.exception.ext.AuthCheckException;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    @Resource
    RedisService redisService;

    public void createUser(User user) {
        userMapper.insertUser(user);
    }

    public Boolean checkUserName(User user) {
        User selectedUser = userMapper.selectUserByName(user.getName());
        return Objects.isNull(selectedUser);
    }

    public User getUserById(Long id) {
        return userMapper.selectUserById(id);
    }

    public void deleteUserById(Long id) {
        userMapper.deleteUserById(id);
    }

    public Cookie userLogin(User user, Cookie cookie) throws DustException {
        // 判断登录
        // 先判断redis中是否存了这个登录状态
        if (!Objects.isNull(cookie)) {
            // cookie非空的情况下，直接判断cookie对应的
            if (redisService.exist(cookie.getValue())) {
                // 如果存在，则说明已经登录
                // 刷新登录状态
                redisService.flushKey(cookie.getValue(), SessionAttributeConstant.SESSION_MAX_INACTIVE_TIME);
                return cookie;
            }
            // 如果不存在呢？
        }
        // 不存在此cookie，则直接执行登录操作
        User loginUser = userMapper.selectUserByUserIdAndPassword(user);
        if (Objects.isNull(loginUser)) {
            throw AuthCheckException.defaultAuthCheckException();
        }
        //  客户的token-id怎么生成呢---UUID
        String uuidToken = UUID.randomUUID().toString();
        redisService.set(uuidToken, JSON.toJSONString(loginUser));
        // 需要返回cookie给controller去set
        return new Cookie(CookieNameDict.COOKIE_LOGIN_TOKEN, uuidToken);
    }

    public Boolean checkLoginStatus(Cookie cookie) {
        if (!Objects.isNull(cookie.getValue())) {
            String cookieValue = cookie.getValue();
            boolean exist = redisService.exist(cookieValue);
            if (exist) {
                redisService.flushKey(cookieValue, SessionAttributeConstant.SESSION_MAX_INACTIVE_TIME);
                return true;
            }
        }
        return false;
    }

    public User getCurrentUser(Cookie cookie) {
        try {
            if (!this.checkLoginStatus(cookie)) {
                return null;
            }
            return JSON.parseObject(redisService.get(cookie.getValue()), User.class);
        } catch (Exception e) {
            return null;
        }
    }
}
