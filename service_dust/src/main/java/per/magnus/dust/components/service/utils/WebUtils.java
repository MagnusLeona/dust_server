package per.magnus.dust.components.service.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class WebUtils {
    public static Cookie getCookieByName(String name, HttpServletRequest httpServletRequest) {
        Cookie[] cookies = httpServletRequest.getCookies();
        List<Cookie> collect = Arrays.stream(cookies).filter(item -> item.getName().equals(name)).collect(Collectors.toList());
        if (collect.isEmpty()) {
            return null;
        }
        return collect.get(0);
    }
}
