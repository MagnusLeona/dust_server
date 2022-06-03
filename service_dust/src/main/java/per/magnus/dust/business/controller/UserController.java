package per.magnus.dust.business.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import per.magnus.dust.business.domain.User;
import per.magnus.dust.business.service.UserService;
import per.magnus.dust.components.service.dict.CookieNameDict;
import per.magnus.dust.components.service.utils.WebUtils;
import per.magnus.dust.components.web.aspect.annotation.LoginCheckRequired;
import per.magnus.dust.components.web.entity.DustResponse;
import per.magnus.dust.components.web.exception.DustException;
import per.magnus.dust.components.web.resolver.annotation.InjectUser;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/user")
@SuppressWarnings("unused")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/create")
    public DustResponse createUser(@RequestBody User user) {
        return DustResponse.okResponse();
    }

    @RequestMapping("/register")
    public DustResponse register(@RequestBody User user) {
        // 注册
        userService.createUser(user);
        return DustResponse.okResponse(user);
    }

    @RequestMapping("/check-name")
    public DustResponse checkUserName(@RequestBody User user) {
        Boolean aBoolean = userService.checkUserName(user);
        return DustResponse.okResponse(aBoolean);
    }

    @RequestMapping("/query/{id}")
    public DustResponse queryUserById(@PathVariable(name = "id") Long id) {
        return DustResponse.okResponse();
    }

    @RequestMapping("/login")
    public DustResponse userLogin(@RequestBody User user, @Nullable
    @CookieValue(value = CookieNameDict.COOKIE_LOGIN_TOKEN, required = false) Cookie cookie,
                                  HttpServletResponse httpServletResponse) throws DustException {
        Cookie loginCookie = userService.userLogin(user, cookie);
        loginCookie.setPath("/");
        httpServletResponse.addCookie(loginCookie);
        // 不为空，则需要保存到session中
        return DustResponse.okResponse();
    }

    @RequestMapping("/login/test")
    @LoginCheckRequired
    public DustResponse userLoginTest(@RequestBody User user, HttpSession httpSession) {
        //
        User loginInfo = (User) httpSession.getAttribute("loginInfo");
        System.out.println("the login session is as follows: " + JSON.toJSONString(loginInfo));
        return DustResponse.okResponse(loginInfo);
    }

    @RequestMapping("/check-login-status")
    public DustResponse checkLoginStatus(@Nullable @CookieValue(CookieNameDict.COOKIE_LOGIN_TOKEN) Cookie cookie) {
        if (Objects.isNull(cookie)) {
            return DustResponse.okResponse(false);
        }
        Boolean login = userService.checkLoginStatus(cookie);
        return DustResponse.okResponse(login);
    }

    @RequestMapping("/get")
    public DustResponse queryCurrentUser(@InjectUser User user) {
        return DustResponse.okResponse(user);
    }
}
