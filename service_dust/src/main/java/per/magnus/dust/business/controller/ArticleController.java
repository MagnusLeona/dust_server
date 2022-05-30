package per.magnus.dust.business.controller;

import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import per.magnus.dust.business.domain.Article;
import per.magnus.dust.business.domain.User;
import per.magnus.dust.business.domain.UserArticle;
import per.magnus.dust.business.service.ArticleService;
import per.magnus.dust.business.service.UserService;
import per.magnus.dust.components.service.dict.CookieNameDict;
import per.magnus.dust.components.service.utils.FileUtils;
import per.magnus.dust.components.web.entity.DustResponse;
import per.magnus.dust.components.web.exception.ext.UserArticleException;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/article")
@SuppressWarnings("unused")
public class ArticleController {

    @Resource
    ArticleService articleService;

    @Resource
    UserService userService;

    @GetMapping("/get")
    public DustResponse getArticleById(Long id) throws IOException {
        // 根据id查询文章
        String s = FileUtils.readFileAsString("C:\\Magnus\\articles\\1.md");
        return DustResponse.okResponse(s);
    }

    @GetMapping("/get/detail/{id}")
    public DustResponse getArticleDetailById(@PathVariable(name = "id") Long id, @Nullable @CookieValue(CookieNameDict.COOKIE_LOGIN_TOKEN) Cookie cookie) throws IOException, UserArticleException {
        // 判断是否登录，如果登录了，那么需要返回是否点赞等数据
        User user = userService.getCurrentUser(cookie);
        UserArticle articleDetail = articleService.getArticleDetail(id, user);
        return DustResponse.okResponse(articleDetail);
    }

    @GetMapping("/get/list/{type}")
    public DustResponse getArticleByType(@PathVariable(name = "type") Integer type) {
        List<Article> articlesByType = articleService.getArticlesByType(type);
        return DustResponse.okResponse(articlesByType);
    }
}
