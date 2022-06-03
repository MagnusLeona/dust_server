package per.magnus.dust.business.controller;

import org.springframework.web.bind.annotation.*;
import per.magnus.dust.business.domain.Article;
import per.magnus.dust.business.domain.User;
import per.magnus.dust.business.domain.UserArticle;
import per.magnus.dust.business.service.UserArticleService;
import per.magnus.dust.components.service.enums.article.ArticleAuthEnum;
import per.magnus.dust.components.web.aspect.annotation.LoginCheckRequired;
import per.magnus.dust.components.web.entity.DustResponse;
import per.magnus.dust.components.web.exception.DustException;
import per.magnus.dust.components.web.resolver.annotation.InjectUser;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user/article")
@SuppressWarnings("unused")
public class UserArticleController {

    @Resource
    UserArticleService userArticleService;

    @RequestMapping("/create")
    @LoginCheckRequired
    public DustResponse createArticle(@InjectUser User user, @RequestBody Map<String, Object> articleMap) throws DustException, IOException {
        Article article = Article.createArticleFromMap(articleMap);
        // 保存作品
        UserArticle userArticle = new UserArticle(user, article);
        userArticle.setRole(ArticleAuthEnum.ARTICLE_AUTH_CREATOR.getAuth());
        userArticleService.createArticleForUser(userArticle);
        return DustResponse.okResponse();
    }

    @GetMapping("/get")
    public DustResponse getArticleByUser(@InjectUser User user) {
        // 返回用户创建的文章
        List<Article> articles = userArticleService.queryArticlesForUser(user);
        return DustResponse.okResponse(articles);
    }

    @GetMapping("/marked/get")
    public DustResponse getMarkedArticles(@InjectUser User user) {
        List<Article> articles = userArticleService.queryMarkedArticles(user);
        return DustResponse.okResponse(articles);
    }

    @GetMapping("/like/try/{id}")
    public DustResponse articleLiked(@InjectUser User user, @PathVariable("id") Long articleId) {
        // 新增点赞数据
        Article article = new Article();
        article.setId(articleId);
        userArticleService.likeArticle(user, article);
        return DustResponse.okResponse();
    }

    @GetMapping("/like/cancel/{id}")
    public DustResponse articleLikeCanceled(@InjectUser User user, @PathVariable("id") Long id) {
        Article article = new Article();
        article.setId(id);
        // 取消点赞
        userArticleService.cancelLikeArticle(user, article);
        return DustResponse.okResponse();
    }

    @GetMapping("/mark/try/{id}")
    public DustResponse articleMarked(@InjectUser User user, @PathVariable("id") Long articleId) {
        // 收藏文章
        Article article = new Article();
        article.setId(articleId);
        userArticleService.markArticle(user, article);
        return DustResponse.okResponse();
    }

    @GetMapping("/mark/cancel/{id}")
    public DustResponse articleMarkCanceled(@InjectUser User user, @PathVariable("id") Long articleId) {
        Article article = new Article();
        article.setId(articleId);
        userArticleService.cancelMarkArticle(user, article);
        return DustResponse.okResponse();
    }
}
